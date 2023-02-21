package com.kilometer.domain.archive.service;

import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.domain.Archive;
import com.kilometer.domain.archive.request.ArchiveCreateRequest;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.exception.ItemNotFoundException;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import com.kilometer.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArchiveEntityMapper {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ArchiveEntity mapToArchiveEntity(final Long userId, final ArchiveCreateRequest request) {
        Archive archive = request.toDomain();

        ArchiveEntity archiveEntity = createArchiveEntity(userId, request.getItemId(), archive);
        archiveEntity.initArchiveImages(archive.toArchiveImageEntities());
        archiveEntity.initUserVisitPlaces(archive.createUserVisitPlaceEntities());
        return archiveEntity;
    }

    private ArchiveEntity createArchiveEntity(final Long userId, final Long itemId, final Archive archive) {
        User user = getUser(userId);
        ItemEntity itemEntity = getItem(itemId);
        return archive.toEntity(user, itemEntity);
    }

    private User getUser(final Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("저장되지 않은 사용자 입니다."));
    }

    private ItemEntity getItem(final Long itemId) {
        ItemEntity itemEntity = itemRepository.findById(itemId)
            .orElseThrow(() -> new ItemNotFoundException("저장되지 않은 아이템 입니다."));
        itemEntity.validateExposureTypeIsOn();
        return itemEntity;
    }
}
