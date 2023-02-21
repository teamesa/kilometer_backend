package com.kilometer.domain.archive.service;

import static com.kilometer.common.statics.Statics.아카이브_공개_설정;
import static com.kilometer.common.statics.Statics.아카이브_별점;
import static com.kilometer.common.statics.Statics.아카이브_코멘트;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kilometer.common.annotation.SpringTestWithData;
import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.dto.PlaceInfo;
import com.kilometer.domain.archive.request.ArchiveCreateRequest;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.item.exception.ItemExposureOffException;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import com.kilometer.domain.user.exception.UserNotFoundException;
import com.kilometer.exception.KilometerErrorCode;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringTestWithData
class ArchiveEntityMapperTest {

    private final List<String> 방문_사진 = List.of("photoUrls");
    private final List<PlaceInfo> 근처_맛집 = List.of(new PlaceInfo("FOOD", "맛집", "address", "roadAddress"));

    @Autowired
    private ArchiveEntityMapper archiveEntityMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("아카이브 엔티티를 매핑한다.")
    void mapToArchiveEntity() {
        // given
        User user = 회원가입을_한다();
        ItemEntity item = 전시_아이템을_등록한다();
        ArchiveCreateRequest request = new ArchiveCreateRequest(item.getId(), 아카이브_코멘트, 아카이브_별점, 아카이브_공개_설정, 방문_사진,
            근처_맛집);

        // when
        ArchiveEntity actual = archiveEntityMapper.mapToArchiveEntity(user.getId(), request);

        // then
        assertAll(
            () -> assertThat(actual.getComment()).isEqualTo(아카이브_코멘트),
            () -> assertThat(actual.getStarRating()).isEqualTo(아카이브_별점),
            () -> assertThat(actual.getUser().getId()).isEqualTo(user.getId()),
            () -> assertThat(actual.getItem().getId()).isEqualTo(item.getId())
        );
    }

    @Test
    @DisplayName("아카이브 엔티티를 매핑 할 때, 사용자가 존재하지 않으면 예외가 발생한다.")
    void mapToArchiveEntity_notExistUser() {
        // given
        Long invalidUserId = 1L;

        ItemEntity 아이템 = 전시_아이템을_등록한다();
        ArchiveCreateRequest request = new ArchiveCreateRequest(아이템.getId(), 아카이브_코멘트, 아카이브_별점, 아카이브_공개_설정, 방문_사진,
            근처_맛집);

        // when & then
        assertThatThrownBy(() -> archiveEntityMapper.mapToArchiveEntity(invalidUserId, request))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("저장되지 않은 사용자 입니다.");
    }

    @Test
    @DisplayName("아카이브 엔티티를 매핑 할 때, 미전시 전시글에 아카이브를 등록하려 하면 예외가 발생한다.")
    void mapToArchiveEntity_exposureOffItem() {
        // given
        User user = 회원가입을_한다();
        ItemEntity item = 미전시_아이템을_등록한다();
        ArchiveCreateRequest request = new ArchiveCreateRequest(item.getId(), 아카이브_코멘트, 아카이브_별점, 아카이브_공개_설정, 방문_사진,
            근처_맛집);

        // when
        ItemExposureOffException actual = assertThrows(ItemExposureOffException.class,
            () -> archiveEntityMapper.mapToArchiveEntity(user.getId(), request));

        // then
        assertEquals(actual.getErrorCode(), KilometerErrorCode.ITEM_EXPOSURE_OFF);
        assertEquals(actual.getMessage(), "미전시 아이템은 아카이빙 할 수 없습니다.");
    }

    private User 회원가입을_한다() {
        User user = User.builder()
            .name("user")
            .email("user@email.com")
            .build();
        return userRepository.save(user);
    }

    private ItemEntity 전시_아이템을_등록한다() {
        ItemEntity item = ItemEntity.builder()
            .exposureType(ExposureType.ON)
            .exhibitionType(ExhibitionType.EXHIBITION)
            .regionType(RegionType.CHUNGCHEONG)
            .feeType(FeeType.FREE)
            .listImageUrl("listImageUrl")
            .thumbnailImageUrl("thumbnailImageUrl")
            .title("title")
            .placeName("placeName")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now())
            .build();
        return itemRepository.save(item);
    }

    private ItemEntity 미전시_아이템을_등록한다() {
        ItemEntity item = ItemEntity.builder()
            .exposureType(ExposureType.OFF)
            .exhibitionType(ExhibitionType.EXHIBITION)
            .regionType(RegionType.CHUNGCHEONG)
            .feeType(FeeType.FREE)
            .listImageUrl("listImageUrl")
            .thumbnailImageUrl("thumbnailImageUrl")
            .title("title")
            .placeName("placeName")
            .startDate(LocalDate.now())
            .endDate(LocalDate.now())
            .build();
        return itemRepository.save(item);
    }
}
