package com.kilometer.domain.archive.service;

import static com.kilometer.common.statics.Statics.아카이브_공개_설정;
import static com.kilometer.common.statics.Statics.아카이브_별점;
import static com.kilometer.common.statics.Statics.아카이브_코멘트;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.kilometer.common.annotation.SpringTestWithData;
import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.dto.PlaceInfo;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.item.exception.ItemExposureOffException;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import com.kilometer.exception.KilometerErrorCode;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringTestWithData
class ArchiveMapperTest {

    private final List<String> 방문_사진 = List.of("photoUrls");
    private final List<PlaceInfo> 근처_맛집 = List.of(new PlaceInfo("FOOD", "맛집", "address", "roadAddress"));

    @Autowired
    private ArchiveMapper archiveMapper;

    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("아카이브 엔티티를 매핑 할 때, 사용자가 존재하지 않으면 예외가 발생한다.")
    void mapToArchiveEntity() {
        // given
        Long invalidUserId = 1L;

        ItemEntity 아이템 = 아이템을_등록한다();
        ArchiveRequest request = new ArchiveRequest(아이템.getId(), 아카이브_코멘트, 아카이브_별점, 아카이브_공개_설정, 방문_사진,
            근처_맛집);

        // when & then
        assertThatThrownBy(() -> archiveMapper.mapToArchive(invalidUserId, request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("저장되지 않은 사용자 입니다.");
    }

    @Test
    @DisplayName("아카이브 정보를 등록할때, 이미 등록한 Archive를 다시 등록하려 하면 예외가 발생한다.")
    void saveArchive_duplicate() {
        // given
        User 회원 = 회원가입을_한다();
        ItemEntity 아이템 = 아이템을_등록한다();
        ArchiveRequest request = new ArchiveRequest(아이템.getId(), 아카이브_코멘트, 아카이브_별점, 아카이브_공개_설정, 방문_사진,
            근처_맛집);
        ArchiveInfo 아카이브_생성_응답 = archiveService.save(회원.getId(), request);

        // when & then
        assertThatThrownBy(() -> archiveMapper.mapToArchive(회원.getId(), request))
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage(
                String.format("이미 등록한 Archive가 있습니다. sItemId : %d / UserId : %d", 아카이브_생성_응답.getId(), 회원.getId()));
    }

    @Test
    @DisplayName("아카이브 정보를 등록할때, 미전시 전시글에 아카이브를 등록하려 하면 예외가 발생한다.")
    void saveArchive_exposureOffItem() {
        // given
        User user = 회원가입을_한다();
        ItemEntity item = ItemEntity.builder().exposureType(ExposureType.OFF).build();
        itemRepository.save(item);
        ArchiveRequest request = new ArchiveRequest(item.getId(), 아카이브_코멘트, 아카이브_별점, 아카이브_공개_설정, 방문_사진, 근처_맛집);

        // when
        ItemExposureOffException actual = assertThrows(ItemExposureOffException.class,
            () -> archiveMapper.mapToArchive(user.getId(), request));

        // then
        assertEquals(actual.getErrorCode(), KilometerErrorCode.ITEM_EXPOSURE_OFF);
    }

    private User 회원가입을_한다() {
        User user = User.builder()
            .name("user")
            .email("user@email.com")
            .build();
        return userRepository.save(user);
    }

    private ItemEntity 아이템을_등록한다() {
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
}
