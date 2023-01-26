package com.kilometer.domain.archive;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ArchiveServiceTest {

    @Autowired
    private ArchiveService archiveService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @MockBean
    private ArchiveRepository archiveRepository;

    @Test
    @DisplayName("아카이브 정보를 등록한다.")
    void saveArchive() {
        // given
        User user = User.builder()
                .name("user")
                .email("user@email.com")
                .build();
        userRepository.save(user);

        ArchiveRequest request = new ArchiveRequest(1L, "comment", 1, true, List.of(), List.of());

        Archive dummyArchive = Archive.builder()
                .id(1L)
                .comment("comment")
                .build();
        given(archiveRepository.save(any())).willReturn(dummyArchive);

        // when
        ArchiveInfo actual = archiveService.save(user.getId(), request);

        // then
        assertThat(actual.getId()).isEqualTo(1L);
        assertThat(actual.getComment()).isEqualTo("comment");
    }

    @Test
    @DisplayName("아카이브 정보를 등록할 때, 이미 등록한 아카이브면 예외가 발생한다.")
    void saveArchive_duplicateArchive() {
        // given
        given(archiveRepository.existsByItemIdAndUserId(any(), any())).willReturn(false);

        Long dummyUserId = 1L;
        ArchiveRequest request = new ArchiveRequest(1L, "comment", 1, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.save(dummyUserId, request))
                .isInstanceOf(IllegalArgumentException.class);
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

        ArchiveRequest request = new ArchiveRequest(1L, null, 1, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.save(user.getId(), request))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Comment must not be null");
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

        ArchiveRequest request = new ArchiveRequest(1L, "comment", 1, true, null, List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.save(user.getId(), request))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Photo urls must not be null");
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

        ArchiveRequest request = new ArchiveRequest(1L, "comment", 1, true, List.of(), null);

        // when & then
        assertThatThrownBy(() -> archiveService.save(user.getId(), request))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Place infos must not be null");
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

        ArchiveRequest request = new ArchiveRequest(1L, "comment", 0, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.save(user.getId(), request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("별점은 1이상 5이하의 숫자여야 합니다. now : " + 0);
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

        ArchiveRequest request = new ArchiveRequest(1L, "comment", 6, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.save(user.getId(), request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("별점은 1이상 5이하의 숫자여야 합니다. now : " + 6);
    }

    @Test
    @DisplayName("아카이브 정보를 등록할 때, 사용자가 존재하지 않으면 예외가 발생한다.")
    void saveArchive_notExistUser() {
        // given
        Long dummyUserId = 1L;
        ArchiveRequest request = new ArchiveRequest(1L, "comment", 6, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveService.save(dummyUserId, request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 사용자 정보 입니다.");
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
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Archive does not exist.");
    }
}
