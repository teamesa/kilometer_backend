package com.kilometer.domain.archive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.dto.ArchiveResponse;
import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.archive.dto.PlaceInfo;
import com.kilometer.domain.archive.exception.ArchiveDuplicateException;
import com.kilometer.domain.archive.exception.ArchiveNotFoundException;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import com.kilometer.domain.archive.request.ArchiveCreateRequest;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import com.kilometer.domain.item.enumType.RegionType;
import com.kilometer.domain.paging.RequestPagingStatus;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class ArchiveServiceTest {

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
        User user = User.builder()
                .name("user")
                .email("user@email.com")
                .build();
        User savedUser = userRepository.save(user);

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
        ItemEntity savedItem = itemRepository.save(item);

        List<String> photoUrls = List.of("photoUrls");
        List<PlaceInfo> placeInfos = List.of(new PlaceInfo("FOOD", "맛집", "address", "roadAddress"));
        ArchiveCreateRequest request = new ArchiveCreateRequest(savedItem.getId(), "comment", 1, true, photoUrls,
                placeInfos);

        // when
        ArchiveInfo actual = archiveService.save(savedUser.getId(), request);

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
        User user = User.builder()
                .name("user")
                .email("user@email.com")
                .build();
        User savedUser = userRepository.save(user);

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
        ItemEntity savedItem = itemRepository.save(item);

        List<String> photoUrls = List.of("photoUrls");
        List<PlaceInfo> placeInfos = List.of(new PlaceInfo("FOOD", "맛집", "address", "roadAddress"));
        ArchiveCreateRequest request = new ArchiveCreateRequest(savedItem.getId(), "comment", 1, true, photoUrls,
                placeInfos);

        archiveService.save(savedUser.getId(), request);

        // when & then
        assertThatThrownBy(() -> archiveService.save(savedUser.getId(), request))
                .isInstanceOf(ArchiveDuplicateException.class);
    }

    @Test
    @DisplayName("아카이브 정보를 등록할 때, 코멘트가 null이면 예외가 발생한다.")
    void saveArchive_nullComment() {
        // given
        User user = User.builder()
                .name("user")
                .email("user@email.com")
                .build();
        userRepository.save(user);

        ArchiveCreateRequest request = new ArchiveCreateRequest(1L, null, 1, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.save(user.getId(), request))
                .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("아카이브 정보를 등록할 때, photoUrls가 null이면 예외가 발생한다.")
    void saveArchive_nullPhotoUrls() {
        // given
        User user = User.builder()
                .name("user")
                .email("user@email.com")
                .build();
        userRepository.save(user);

        ArchiveCreateRequest request = new ArchiveCreateRequest(1L, "comment", 1, true, null, List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.save(user.getId(), request))
                .isInstanceOf(ArchiveValidationException.class)
                .hasMessage("입력된 전시회 사진 URL이 없습니다.");
    }

    @Test
    @DisplayName("아카이브 정보를 등록할 때, photoInfos가 null이면 예외가 발생한다.")
    void saveArchive_nullPhotoInfos() {
        // given
        User user = User.builder()
                .name("user")
                .email("user@email.com")
                .build();
        userRepository.save(user);

        ArchiveCreateRequest request = new ArchiveCreateRequest(1L, "comment", 1, true, List.of(), null);

        // when & then
        assertThatThrownBy(() -> archiveService.save(user.getId(), request))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("아카이브 정보를 등록할 때, starRating이 1보다 작으면 예외가 발생한다.")
    void saveArchive_lowStarRating() {
        // given
        User user = User.builder()
                .name("user")
                .email("user@email.com")
                .build();
        userRepository.save(user);

        ArchiveCreateRequest request = new ArchiveCreateRequest(1L, "comment", 0, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.save(user.getId(), request))
                .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("아카이브 정보를 등록할 때, starRating이 5보다 크면 예외가 발생한다.")
    void saveArchive_highStarRating() {
        // given
        User user = User.builder()
                .name("user")
                .email("user@email.com")
                .build();
        userRepository.save(user);

        ArchiveCreateRequest request = new ArchiveCreateRequest(1L, "comment", 6, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.save(user.getId(), request))
                .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("아카이브 정보를 등록할 때, 사용자가 존재하지 않으면 예외가 발생한다.")
    void saveArchive_notExistUser() {
        // given
        Long dummyUserId = 1L;
        ArchiveCreateRequest request = new ArchiveCreateRequest(dummyUserId, "comment", 1, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.save(dummyUserId, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 사용자 입니다.");
    }

    @Test
    @DisplayName("아카이브 정보를 수정한다.")
    void updateArchive() {
        // given
        User user = User.builder()
                .name("user")
                .email("user@email.com")
                .build();
        User savedUser = userRepository.save(user);

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
        ItemEntity savedItem = itemRepository.save(item);

        List<String> photoUrls = List.of("photoUrls");
        List<PlaceInfo> placeInfos = List.of(new PlaceInfo("FOOD", "맛집", "address", "roadAddress"));
        ArchiveCreateRequest request = new ArchiveCreateRequest(savedItem.getId(), "comment", 1, true, photoUrls,
                placeInfos);

        archiveService.save(savedUser.getId(), request);

        List<String> photoUrlsForUpdate = List.of("updatedPhotoUrls");
        List<PlaceInfo> placeInfosForUpdate = List.of(new PlaceInfo("FOOD", "수정된 맛집", "address", "roadAddress"));
        ArchiveRequest requestForUpdate = new ArchiveRequest(savedItem.getId(), "updatedComment", 3, true,
                photoUrlsForUpdate, placeInfosForUpdate);

        // when
        ArchiveInfo actual = archiveService.update(savedUser.getId(), requestForUpdate);

        // then
        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getComment()).isEqualTo("updatedComment"),
                () -> assertThat(actual.getPhotoUrls()).contains("updatedPhotoUrls"),
                () -> assertThat(actual.getFood()).contains("수정된 맛집")
        );
    }

    @Test
    @DisplayName("아카이브 정보를 수정할 때, 사용자 id가 null이면 예외가 발생한다.")
    void updateArchive_nullUserId() {
        // given
        Long dummyUserId = null;
        ArchiveRequest request = new ArchiveRequest(1L, "comment", 6, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.update(dummyUserId, request))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("id must not be null");
    }

    @Test
    @DisplayName("아카이브 정보를 수정할 때, itemId가 null이면 예외가 발생한다.")
    void updateArchive_nullItemId() {
        // given
        Long dummyUserId = 1L;
        ArchiveRequest request = new ArchiveRequest(null, "comment", 6, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.update(dummyUserId, request))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Item id must not be null");
    }

    @Test
    @DisplayName("아카이브 정보를 수정할 때, userId와 itemId에 해당하는 저장된 Archive가 없으면 발생한다.")
    void updateArchive_notExistsArchive() {
        // given
        Long dummyUserId = 1L;
        ArchiveRequest request = new ArchiveRequest(1L, "comment", 6, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.update(dummyUserId, request))
                .isInstanceOf(ArchiveNotFoundException.class);
    }

    @Test
    @DisplayName("회원의 id와 아이템 id에 해당하는 아카이브 정보를 조회한다.")
    void findArchiveByItemIdAndUserId() {
        // given
        User user = User.builder()
                .name("user")
                .email("user@email.com")
                .build();
        User savedUser = userRepository.save(user);

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
        ItemEntity savedItem = itemRepository.save(item);

        List<String> photoUrls = List.of("photoUrls");
        List<PlaceInfo> placeInfos = List.of(new PlaceInfo("FOOD", "맛집", "address", "roadAddress"));
        ArchiveCreateRequest request = new ArchiveCreateRequest(savedItem.getId(), "comment", 1, true, photoUrls,
                placeInfos);

        archiveService.save(savedUser.getId(), request);

        RequestPagingStatus pagingRequest = new RequestPagingStatus(0, 2, 0);

        // when
        ArchiveResponse actual = archiveService.findAllByItemIdAndUserId(savedItem.getId(),
                savedUser.getId(), pagingRequest, ArchiveSortType.LIKE_DESC);

        // then
        assertThat(actual.getArchives()).hasSize(1);
    }

    @Test
    @DisplayName("회원의 id와 아이템 id에 해당하는 아카이브 정보를 조회할때, 아카이브 정보가 없으면 빈 리스트를 반환한다.")
    void findArchiveByItemIdAndUserId_emptyList() {
        // given
        User user = User.builder()
                .name("user")
                .email("user@email.com")
                .build();
        User savedUser = userRepository.save(user);

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
        ItemEntity savedItem = itemRepository.save(item);

        List<String> photoUrls = List.of("photoUrls");
        List<PlaceInfo> placeInfos = List.of(new PlaceInfo("FOOD", "맛집", "address", "roadAddress"));
        ArchiveCreateRequest request = new ArchiveCreateRequest(savedItem.getId(), "comment", 1, true, photoUrls,
                placeInfos);

        archiveService.save(savedUser.getId(), request);

        RequestPagingStatus pagingRequest = new RequestPagingStatus(1, 1, 0);

        // when
        ArchiveResponse actual = archiveService.findAllByItemIdAndUserId(savedItem.getId(),
                savedUser.getId(), pagingRequest, ArchiveSortType.LIKE_DESC);

        // then
        assertThat(actual.getArchives()).hasSize(0);
    }
}
