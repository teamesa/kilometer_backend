package com.kilometer.domain.archive.service;

import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.ArchiveRepository;
import com.kilometer.domain.archive.domain.Archive;
import com.kilometer.domain.archive.exception.ArchiveDuplicateException;
import com.kilometer.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ArchiveEntityMapper {

    private final ArchiveRepository archiveRepository;
    private final UserRepository userRepository;

    public ArchiveEntityMapper(final ArchiveRepository archiveRepository, final UserRepository userRepository) {
        this.archiveRepository = archiveRepository;
        this.userRepository = userRepository;
    }

    public ArchiveEntity createArchiveEntity(final Archive archive, final Long itemId, final Long userId) {
        validateNotDuplicate(archive, itemId, userId);
        return null;
    }

    private void validateNotDuplicate(final Archive archive, final Long itemId, final Long userId) {
        if (archiveRepository.existsByItemIdAndUserId(itemId, userId)) {
            throw new ArchiveDuplicateException(
                    String.format("기존에 등록한 Archive가 있습니다. sItemId : %d / UserId : %d", itemId, userId));
        }
    }
}
