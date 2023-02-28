package com.kilometer.domain.homeModules.modules.swipeItem;

import static com.kilometer.common.Fixture.LOWER_TITLE;
import static com.kilometer.common.Fixture.MAX_DATE;
import static com.kilometer.common.Fixture.MIN_DATE;
import static com.kilometer.common.Fixture.TITLE;
import static com.kilometer.common.Fixture.TOP_TITLE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.kilometer.common.annotation.SpringTestWithData;
import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.Module;
import com.kilometer.domain.homeModules.modules.ModuleHandler;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import com.kilometer.domain.homeModules.modules.swipeItem.dto.SwipeItemDataDto;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.item.itemDetail.ItemDetail;
import com.kilometer.domain.item.itemDetail.ItemDetailRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("SwipeItemHandler 는 ")
@SpringTestWithData
class SwipeItemHandlerTest {

    @Autowired
    ModuleHandler swipeItemHandler;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemDetailRepository itemDetailRepository;

    @DisplayName("모듈 타입이 SwipeItem 인지를 확인한다.")
    @Test
    void supports() {
        assertThat(swipeItemHandler.supports(ModuleType.SWIPE_ITEM)).isTrue();
    }

    @DisplayName("SwipeItem 모듈에 필요한 데이터를 조회할 때 ")
    @Nested
    class SwipeItemGenerationTest {

        @DisplayName("조건을 모두 만족하면 SwipeItem 데이터를 반환한다.")
        @Test
        void generate() {
            ItemEntity itemEntity = saveItem(TITLE);
            saveItemDetail(itemEntity);

            Module module = Module.builder()
                    .moduleName(ModuleType.MONTHLY_FREE_ITEM)
                    .upperModuleTitle(TOP_TITLE)
                    .lowerModuleTitle(LOWER_TITLE)
                    .extraData(itemEntity.getId() + "")
                    .build();
            SwipeItemDataDto swipeItem = (SwipeItemDataDto) generateSwipeItem(module).get();

            assertAll(
                    () -> assertThat(swipeItem.getTitle().getText()).isEqualTo(itemEntity.getTitle()),
                    () -> assertThat(swipeItem.getThumbnailPhotoUrl()).isEqualTo(itemEntity.getThumbnailImageUrl())
            );
        }

        @DisplayName("ModuleDto 가 null 이면 에러를 던진다.")
        @Test
        void generateFailDueToNullModuleDto() {
            ItemEntity itemEntity = saveItem(TITLE);
            saveItemDetail(itemEntity);

            assertThatThrownBy(() -> generateSwipeItem(null))
                    .isInstanceOf(NullPointerException.class);
        }

        @DisplayName("ExtraData 가 null 이면 에러를 던진다.")
        @Test
        void generateFailDueToNullExtraData() {
            ItemEntity itemEntity = saveItem(TITLE);
            saveItemDetail(itemEntity);

            Module module = Module.builder()
                    .moduleName(ModuleType.MONTHLY_FREE_ITEM)
                    .upperModuleTitle(TOP_TITLE)
                    .lowerModuleTitle(LOWER_TITLE)
                    .extraData(null)
                    .build();

            assertThatThrownBy(() -> generateSwipeItem(module))
                    .isInstanceOf(NullPointerException.class);
        }

        @DisplayName("조회된 SwipeItem 데이가 유효하지 않다면 데이터를 반환하지 않는다.")
        void generateFailDueToInvalideData() {
            ItemEntity itemEntity = saveItem(TITLE);
            saveItemDetail(itemEntity);

            Module module = Module.builder()
                    .moduleName(ModuleType.MONTHLY_FREE_ITEM)
                    .upperModuleTitle(TOP_TITLE)
                    .lowerModuleTitle(LOWER_TITLE)
                    .extraData(null)
                    .build();

            assertThat(generateSwipeItem(module).isEmpty()).isTrue();
        }

        private ItemEntity saveItem(String title) {
            ItemEntity itemEntity = ItemEntity.builder()
                    .exhibitionType(ExhibitionType.EXHIBITION)
                    .exposureType(ExposureType.ON)
                    .regionType(RegionType.CHUNGCHEONG)
                    .feeType(FeeType.FREE)
                    .listImageUrl("listImageUrl")
                    .thumbnailImageUrl("thumbnailImageUrl")
                    .title(title)
                    .placeName("place")
                    .latitude(12.34)
                    .longitude(45.67)
                    .price("123")
                    .homepageUrl("hompageUrl")
                    .ticketUrl("ticketUrl")
                    .operatingTime("operatingTIme")
                    .pickCount(1)
                    .startDate(MIN_DATE)
                    .endDate(MAX_DATE)
                    .build();
            return itemRepository.save(itemEntity);
        }

        private void saveItemDetail(ItemEntity itemEntity) {
            ItemDetail itemDetail = ItemDetail.builder()
                    .introduce("introduction")
                    .item(itemEntity)
                    .build();
            itemDetailRepository.save(itemDetail);
        }

        private Optional<?> generateSwipeItem(Module module) {
            ModuleParamDto moduleParamDto = ModuleParamDto.of(LocalDateTime.now(), 1L,
                    ModuleDto.from(module));
            return swipeItemHandler.generator(moduleParamDto);
        }
    }
}
