package com.kilometer.domain.archive.domain.archiveimages;

import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import java.util.List;
import java.util.stream.Collectors;

public class ArchiveImages {

    private final List<ArchiveImage> archiveImages;

    public ArchiveImages(final List<String> photoUrls) {
        this.archiveImages = photoUrls.stream()
                .map(ArchiveImage::createArchiveImage)
                .collect(Collectors.toList());
    }

    public List<ArchiveImageEntity> toEntity() {
        return archiveImages.stream()
                .map(ArchiveImage::toEntity)
                .collect(Collectors.toList());
    }
}
