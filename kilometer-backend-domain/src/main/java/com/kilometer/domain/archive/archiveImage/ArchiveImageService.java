package com.kilometer.domain.archive.archiveImage;

import com.kilometer.domain.archive.Archive;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArchiveImageService {

    private final ArchiveImageRepository archiveImageRepository;

    @Transactional
    public List<ArchiveImage> saveAll(List<ArchiveImage> archiveImages, Long archiveId) {
        Preconditions.notNull(archiveImages, "Archive images must not be null");
        if(archiveImages.isEmpty()) {
            return List.of();
        }
        Archive archive = Archive.builder().id(archiveId).build();
        archiveImages.forEach(archiveImage -> archiveImage.setArchive(archive));
        return  archiveImageRepository.saveAll(archiveImages);
    }
    @Transactional
    public void deleteAllByArchiveId(Long archiveId) {
        Preconditions.notNull(archiveId, "Archive id must not be null : " + archiveId);
        archiveImageRepository.deleteAllByArchiveId(archiveId);
    }

    public List<ArchiveImage> findAllByArchiveId(Long archiveId) {
        Preconditions.notNull(archiveId, "Archive id must not be null : " + archiveId);
        return archiveImageRepository.findAllByArchiveId(archiveId);
    }

    public boolean existArchiveImagesByArchiveId(Long archiveId) {
        Preconditions.notNull(archiveId, "Archive id must not be null : " + archiveId);
        return archiveImageRepository.existsArchiveImagesByArchiveId(archiveId);
    }
}
