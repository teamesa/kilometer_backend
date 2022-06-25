package com.kilometer.domain.archive.archiveImage;

import com.kilometer.domain.archive.Archive;
import com.kilometer.domain.archive.request.ArchiveRequest;
import java.util.ArrayList;
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
    public void saveAll(ArchiveRequest archiveRequest, Archive archive) {
        if (archiveRequest.getPhotoUrls().isEmpty()) {
            return;
        }
        List<ArchiveImage> archiveImages = archiveRequest.makeArchiveImages();
        archiveImages.forEach(archiveImage -> archiveImage.setArchive(archive));
        archiveImageRepository.saveAll(archiveImages);
    }

    @Transactional
    public void deleteAll(List<ArchiveImage> archiveImages) {
        if (archiveImages.isEmpty()) {
            return;
        }
        archiveImageRepository.deleteAll(archiveImages);
    }

    public List<ArchiveImage> findAllByArchiveId(Long archiveId) {
        Preconditions.notNull(archiveId, "Archive id must not be null : " + archiveId);
        return archiveImageRepository.findAllByArchiveId(archiveId);
    }
}
