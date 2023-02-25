package com.kilometer.domain.archive.archiveImage;

import com.google.common.base.Preconditions;
import com.kilometer.domain.archive.ArchiveEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArchiveImageService {

    private final ArchiveImageRepository archiveImageRepository;

    @Transactional
    public List<ArchiveImageEntity> saveAll(List<ArchiveImageEntity> archiveImageEntities, Long archiveId) {
        Preconditions.checkNotNull(archiveImageEntities, "Archive images must not be null");
        if(archiveImageEntities.isEmpty()) {
            return List.of();
        }
        ArchiveEntity archiveEntity = ArchiveEntity.builder().id(archiveId).build();
        archiveImageEntities.forEach(archiveImage -> archiveImage.initArchiveEntity(archiveEntity));
        return  archiveImageRepository.saveAll(archiveImageEntities);
    }
    @Transactional
    public void deleteAllByArchiveId(Long archiveId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null : " + archiveId);
        archiveImageRepository.deleteAllByArchiveEntityId(archiveId);
    }

    public List<ArchiveImageEntity> findAllByArchiveId(Long archiveId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null : " + archiveId);
        return archiveImageRepository.findAllByArchiveEntityId(archiveId);
    }

    public boolean existArchiveImagesByArchiveId(Long archiveId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null : " + archiveId);
        return archiveImageRepository.existsArchiveImagesByArchiveEntityId(archiveId);
    }
}
