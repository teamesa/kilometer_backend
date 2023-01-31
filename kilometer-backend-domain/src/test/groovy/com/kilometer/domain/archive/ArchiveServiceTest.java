package com.kilometer.domain.archive;

import static com.kilometer.common.statics.Statics.아카이브_공개_설정;
import static com.kilometer.common.statics.Statics.아카이브_별점;
import static com.kilometer.common.statics.Statics.아카이브_코멘트;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.kilometer.common.annotation.SpringTestWithData;
import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.dto.PlaceInfo;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@SpringTestWithData
public class ArchiveServiceTest {

    private final List<String> 방문_사진 = List.of("photoUrls");
    private final List<PlaceInfo> 근처_맛집 = List.of(new PlaceInfo("FOOD", "맛집", "address", "roadAddress"));

    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @DisplayName("아카이브와 관련한 정보를 등록한다.")
    void saveArchive() {
        // given
        User 회원 = 회원가입을_한다();
        ItemEntity 아이템 = 아이템을_등록한다();
        ArchiveRequest request = new ArchiveRequest(아이템.getId(), 아카이브_코멘트, 아카이브_별점, 아카이브_공개_설정, 방문_사진,
            근처_맛집);

        // when
        ArchiveInfo actual = archiveService.save(회원.getId(), request);

        // then
        assertAll(
            () -> assertThat(actual.getId()).isNotNull(),
            () -> assertThat(actual.getComment()).isEqualTo("comment"),
            () -> assertThat(actual.getPhotoUrls()).contains("photoUrls"),
            () -> assertThat(actual.getFood()).contains("맛집")
        );
    }

    @Test
    @DisplayName("아카이브 정보를 등록할때, 이미 등록한 Archive를 다시 등록하려 하면 예외가 발생한다..")
    void saveArchive_duplicate() {
        // given
        User 회원 = 회원가입을_한다();
        ItemEntity 아이템 = 아이템을_등록한다();
        ArchiveRequest request = new ArchiveRequest(아이템.getId(), 아카이브_코멘트, 아카이브_별점, 아카이브_공개_설정, 방문_사진,
            근처_맛집);

        archiveService.save(회원.getId(), request);

        // when & then
        assertThatThrownBy(() -> archiveService.save(회원.getId(), request))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("아카이브 정보를 등록할 때, photoUrls가 null이면 예외가 발생한다.")
    void saveArchive_nullPhotoUrls() {
        // given
        User 회원 = 회원가입을_한다();
        ItemEntity 아이템 = 아이템을_등록한다();

        List<String> invalidPhotoUrls = null;
        ArchiveRequest request = new ArchiveRequest(아이템.getId(), 아카이브_코멘트, 아카이브_별점, 아카이브_공개_설정, invalidPhotoUrls,
            근처_맛집);

        // when & then
        assertThatThrownBy(() -> archiveService.save(회원.getId(), request))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("Photo urls must not be null");
    }

    @Test
    @DisplayName("아카이브 정보를 등록할 때, photoInfos가 null이면 예외가 발생한다.")
    void saveArchive_nullPhotoInfos() {
        // given
        User 회원 = 회원가입을_한다();
        ItemEntity 아이템 = 아이템을_등록한다();

        List<PlaceInfo> invalidPhotoInfos = null;
        ArchiveRequest request = new ArchiveRequest(아이템.getId(), 아카이브_코멘트, 아카이브_별점, 아카이브_공개_설정, 방문_사진,
            invalidPhotoInfos);

        // when & then
        assertThatThrownBy(() -> archiveService.save(회원.getId(), request))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("아카이브 정보를 등록할 때, 사용자가 존재하지 않으면 예외가 발생한다.")
    void saveArchive_notExistUser() {
        // given
        Long invalidUserId = 1L;

        ItemEntity 아이템 = 아이템을_등록한다();
        ArchiveRequest request = new ArchiveRequest(아이템.getId(), 아카이브_코멘트, 아카이브_별점, 아카이브_공개_설정, 방문_사진,
            근처_맛집);

        // when & then
        assertThatThrownBy(() -> archiveService.save(invalidUserId, request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("잘못된 사용자 정보 입니다.");
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
