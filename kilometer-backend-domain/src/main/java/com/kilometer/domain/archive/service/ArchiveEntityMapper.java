package com.kilometer.domain.archive.service;

import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.archive.domain.Archive;
import com.kilometer.domain.archive.exception.ArchiveDuplicateException;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArchiveEntityMapper {

    private final ArchiveRepository archiveRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ArchiveEntityMapper(final ArchiveRepository archiveRepository,
                               final UserRepository userRepository,
                               final ItemRepository itemRepository) {
        this.archiveRepository = archiveRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public ArchiveEntity createArchiveEntity(final Archive archive, final Long itemId, final Long userId) {
        validateNotDuplicate(itemId, userId);
        ArchiveEntity archiveEntity = create(archive, itemId, userId);
        archiveEntity.addArchiveImageEntities(archive.mapToImageEntities());
        archiveEntity.addUserVisitPlaceEntities(archive.mapToVisitPlaceEntities());
        return archiveEntity;
    }

    private void validateNotDuplicate(final Long itemId, final Long userId) {
        if (archiveRepository.existsByItemIdAndUserId(itemId, userId)) {
            throw new ArchiveDuplicateException(
                    String.format("이미 등록한 Archive가 있습니다. itemId : %d, userId : %d", itemId, userId));
        }
    }

    private ArchiveEntity create(final Archive archive, final Long itemId, final Long userId) {
        User user = findUserBy(userId);
        ItemEntity item = findItemBy(itemId);
        return archive.toEntity(user, item);
    }

    private User findUserBy(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자 입니다."));
    }

    private ItemEntity findItemBy(final Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템 입니다."));
    }
}
