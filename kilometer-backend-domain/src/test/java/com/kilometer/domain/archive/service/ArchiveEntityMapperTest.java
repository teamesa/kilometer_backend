package com.kilometer.domain.archive.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.archive.domain.Archive;
import com.kilometer.domain.archive.exception.ArchiveDuplicateException;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ArchiveEntityMapperTest {

    @Autowired
    private ArchiveEntityMapper archiveEntityMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ArchiveRepository archiveRepository;

    @Test
    @DisplayName("Archive를 ArchiveEntity로 매핑한다.")
    void mapToArchiveEntity() {
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

        Archive archive = new Archive(1L, "comment", 1, true, List.of(), List.of());

        // when
        ArchiveEntity actual = archiveEntityMapper.createArchiveEntity(archive, savedItem.getId(),
                savedUser.getId());

        // then
        assertAll(
                () -> assertThat(actual.getComment()).isEqualTo("comment"),
                () -> assertThat(actual.getArchiveImages()).hasSize(0),
                () -> assertThat(actual.getUserVisitPlaces()).hasSize(0)
        );
    }

    @Test
    @DisplayName("존재하지 않는 회원으로 Archive를 매핑하면 예외가 발생한다.")
    void notExistsUser() {
        // given
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

        Long invalidUserId = 1L;
        Archive archive = new Archive(1L, "comment", 1, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveEntityMapper.createArchiveEntity(archive, savedItem.getId(), invalidUserId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 사용자 입니다.");
    }

    @Test
    @DisplayName("존재하지 않는 아이템으로 Archive를 매핑하면 예외가 발생한다.")
    void notExistsItem() {
        // given
        User user = User.builder()
                .name("user")
                .email("user@email.com")
                .build();
        User savedUser = userRepository.save(user);

        Long invalidItemEntityId = 1L;
        Archive archive = new Archive(1L, "comment", 1, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(
                () -> archiveEntityMapper.createArchiveEntity(archive, invalidItemEntityId, savedUser.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 아이템 입니다.");
    }

    @Test
    @DisplayName("이미 등록한 ArchiveEntity가 있으면 예외가 발생한다.")
    void duplicateArchiveEntityException() {
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

        ArchiveEntity archiveEntity = ArchiveEntity.builder()
                .comment("comment")
                .starRating(1)
                .isVisibleAtItem(true)
                .item(savedItem)
                .user(savedUser)
                .build();
        archiveRepository.save(archiveEntity);

        Archive archive = new Archive(1L, "comment", 1, true, List.of(), List.of());

        // when & then
        assertThatThrownBy(() -> archiveEntityMapper.createArchiveEntity(archive, savedItem.getId(), savedUser.getId()))
                .isInstanceOf(ArchiveDuplicateException.class)
                .hasMessage("이미 등록한 Archive가 있습니다. itemId : %d, userId : %d", savedItem.getId(), savedUser.getId());
    }
}
