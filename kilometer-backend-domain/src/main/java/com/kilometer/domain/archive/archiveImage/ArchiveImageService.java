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
    public List<ArchiveImage> saveAll(List<ArchiveImage> archiveImages, Long archiveId) {
        Preconditions.checkNotNull(archiveImages, "Archive images must not be null");
        if(archiveImages.isEmpty()) {
            return List.of();
        }
        ArchiveEntity archiveEntity = ArchiveEntity.builder().id(archiveId).build();
        archiveImages.forEach(archiveImage -> archiveImage.setArchiveEntity(archiveEntity));
        return  archiveImageRepository.saveAll(archiveImages);
    }
    @Transactional
    public void deleteAllByArchiveId(Long archiveId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null : " + archiveId);
        archiveImageRepository.deleteAllByArchiveEntityId(archiveId);
    }

    public List<ArchiveImage> findAllByArchiveId(Long archiveId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null : " + archiveId);
        return archiveImageRepository.findAllByArchiveEntityId(archiveId);
    }

    public boolean existArchiveImagesByArchiveId(Long archiveId) {
        Preconditions.checkNotNull(archiveId, "Archive id must not be null : " + archiveId);
        return archiveImageRepository.existsArchiveImagesByArchiveEntityId(archiveId);
    }
}
