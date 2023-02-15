package com.kilometer.domain.archive.service;

import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.domain.Archive;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.item.ItemRepository;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.exception.ItemExposureOffException;
import com.kilometer.domain.item.exception.ItemNotFoundException;
import com.kilometer.domain.user.User;
import com.kilometer.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArchiveEntityMapper {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public ArchiveEntity mapToArchiveEntity(final Long userId, final ArchiveRequest archiveRequest) {
        Archive archive = archiveRequest.toDomain();
        
        ArchiveEntity archiveEntity = createArchiveEntity(userId, archiveRequest.getItemId(), archive);
        archiveEntity.addArchiveImages(archive.createArchiveImageEntities());
        archiveEntity.addUserVisitPlaces(archive.createUserVisitPlaceEntities());
        return archiveEntity;
    }

    private ArchiveEntity createArchiveEntity(final Long userId, final Long itemId, final Archive archive) {
        User user = getUser(userId);
        ItemEntity itemEntity = getItem(itemId);
        return archive.toEntity(user, itemEntity);
    }

    private User getUser(final Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("저장되지 않은 사용자 입니다."));
    }

    private ItemEntity getItem(final Long itemId) {
        itemRepository.findExposureById(itemId)
            .map(mapping -> {
                if (mapping.getExposureType() == ExposureType.OFF) {
                    throw new ItemExposureOffException();
                }
                return mapping;
            })
            .orElseThrow(ItemNotFoundException::new);

        return itemRepository.findById(itemId)
            .orElseThrow(ItemNotFoundException::new);
    }
}
