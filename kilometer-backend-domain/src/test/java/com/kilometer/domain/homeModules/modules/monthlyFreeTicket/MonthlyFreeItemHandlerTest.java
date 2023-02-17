package com.kilometer.domain.homeModules.modules.monthlyFreeTicket;

import static com.kilometer.common.Fixture.LOWER_TITLE;
import static com.kilometer.common.Fixture.COMMENT;
import static com.kilometer.common.Fixture.LIKE_COUNT;
import static com.kilometer.common.Fixture.MAX_DATE;
import static com.kilometer.common.Fixture.MIN_DATE;
import static com.kilometer.common.Fixture.STAR_RATING;
import static com.kilometer.common.Fixture.TITLE;
import static com.kilometer.common.Fixture.TOP_TITLE;
import static com.kilometer.common.Fixture.USER_EMAIL;
import static com.kilometer.common.Fixture.USER_EMAIL2;
import static com.kilometer.common.Fixture.USER_IMAGE_URL;
import static com.kilometer.common.Fixture.USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kilometer.common.annotation.SpringTestWithData;
import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.homeModules.ModuleParamDto;
import com.kilometer.domain.homeModules.enumType.ModuleType;
import com.kilometer.domain.homeModules.modules.Module;
import com.kilometer.domain.homeModules.modules.dto.ModuleDto;
import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketResponse;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.dto.MonthlyFreeTicketDto;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.pick.Pick;
import com.kilometer.domain.pick.PickRepository;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("MonthlyFreeItemHandler 는 ")
@SpringTestWithData
class MonthlyFreeItemHandlerTest {

    @Autowired
    MonthlyFreeItemHandler monthlyFreeItemHandler;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ArchiveRepository archiveRepository;

    @Autowired
    PickRepository pickRepository;

    @DisplayName("모듈 타입이 이달의 무료 티켓인지 확인한다.")
    @Test
    void supports() {
        boolean actual = monthlyFreeItemHandler.supports(ModuleType.MONTHLY_FREE_ITEM);

        assertThat(actual).isTrue();
    }

    @DisplayName("이달의 무료 티켓이 필요한 데이터를 생성할 때 ")
    @Nested
    class MonthlyFreeTicketGenerationTest {

        @DisplayName("이달의 무료 티켓이 존재하면 데이터를 조회한다.")
        @Test
        void generateExistedMonthlyFreeTicket() {
            User savedUser = saveUser(USER_EMAIL);
            ItemEntity savedItemEntity = saveItem();
            savePick(savedUser, savedItemEntity);
            saveArchive(savedUser, savedItemEntity);

            MonthlyFreeTicketResponse monthlyFreeTicketResponse = (MonthlyFreeTicketResponse) generateMonthlyFreeTicket(savedUser)
                    .get();

            assertAll(
                    () -> assertThat(monthlyFreeTicketResponse.getTopTitle()).isEqualTo(TOP_TITLE),
                    () -> assertThat(monthlyFreeTicketResponse.getBottomTitle()).isEqualTo(LOWER_TITLE),
                    () -> assertThat(monthlyFreeTicketResponse.getMonthlyFreeTicketDtos().size()).isEqualTo(1)
            );
        }

        @DisplayName("로그인한 유저가 픽을한게 아니라면, 픽 여부를 false로 반환한다.")
        @Test
        void generateNonAuthenticatedUserMonthlyFreeTicket() {
            User savedUser = saveUser(USER_EMAIL);
            User savedUser2 = saveUser(USER_EMAIL2);
            ItemEntity savedItemEntity = saveItem();
            savePick(savedUser, savedItemEntity);
            saveArchive(savedUser, savedItemEntity);

            MonthlyFreeTicketResponse monthlyFreeTicketResponse = (MonthlyFreeTicketResponse) generateMonthlyFreeTicket(savedUser2)
                    .get();
            MonthlyFreeTicketDto monthlyFreeTicketDto = monthlyFreeTicketResponse.getMonthlyFreeTicketDtos()
                    .get(0);

            assertThat(monthlyFreeTicketDto.isHearted()).isFalse();
        }

        @DisplayName("존재하지 않으면 데이터를 조회하지 않는다.")
        @Test
        void getEmptyMonthlyFreeTicket() {
            User savedUser = saveUser(USER_EMAIL);

            assertThat(generateMonthlyFreeTicket(savedUser).isEmpty()).isTrue();
        }
    }

    private User saveUser(String email) {
        User user = User.builder()
                .name(USER_NAME)
                .email(email)
                .imageUrl(USER_IMAGE_URL)
                .build();
        return userRepository.save(user);
    }

    private ItemEntity saveItem() {
        ItemEntity item = ItemEntity.builder()
                .exhibitionType(ExhibitionType.EXHIBITION)
                .exposureType(ExposureType.ON)
                .regionType(RegionType.CHUNGCHEONG)
                .feeType(FeeType.FREE)
                .listImageUrl("listImageUrl")
                .thumbnailImageUrl("thumbnailImageUrl")
                .title(TITLE)
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
        return itemRepository.save(item);
    }

    private void savePick(User user, ItemEntity itemEntity) {
        Pick pick = Pick.builder()
                .isHearted(true)
                .pickedItem(itemEntity)
                .pickedUser(user)
                .build();
        pickRepository.save(pick);
    }

    private void saveArchive(User user, ItemEntity itemEntity) {
        ArchiveEntity archive = ArchiveEntity.builder()
                .starRating(STAR_RATING)
                .likeCount(LIKE_COUNT)
                .isVisibleAtItem(true)
                .comment(COMMENT)
                .user(user)
                .item(itemEntity)
                .build();
        archiveRepository.save(archive);
    }

    private Optional<Object> generateMonthlyFreeTicket(User user) {
        Module module = Module.builder()
                .moduleName(ModuleType.MONTHLY_FREE_ITEM)
                .upperModuleTitle(TOP_TITLE)
                .lowerModuleTitle(LOWER_TITLE)
                .build();
        ModuleParamDto moduleParamDto = ModuleParamDto.of(LocalDateTime.now(), user.getId(),
                ModuleDto.from(module));
        return monthlyFreeItemHandler.generator(moduleParamDto);
    }
}
