package com.kilometer.domain.archive.service;

import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.archive.domain.Archive;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import com.kilometer.domain.archive.request.ArchiveRequest;
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
public class ArchiveMapper {

    private final ArchiveRepository archiveRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public Archive mapToArchive(Long userId, ArchiveRequest archiveRequest) {
        Archive archive = archiveRequest.toDomain();
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("저장되지 않은 사용자 입니다."));

        if (archiveRepository.existsByItemIdAndUserId(archiveRequest.getItemId(), userId)) {
            throw new ArchiveValidationException(String.format("이미 등록한 Archive가 있습니다. sItemId : %d / UserId : %d",
                archiveRequest.getItemId(), userId));
        }

        itemRepository.findExposureById(archiveRequest.getItemId())
            .map(mapping -> {
                if (mapping.getExposureType() == ExposureType.OFF) {
                    throw new ItemExposureOffException();
                }
                return mapping;
            })
            .orElseThrow(ItemNotFoundException::new);

        return archive;
    }
}
