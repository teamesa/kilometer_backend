package com.kilometer.domain.archive.domain.archiveImages;

import java.util.List;
import java.util.stream.Collectors;

public class ArchiveImages {

    private final List<ArchiveImage> archiveImages;

    public ArchiveImages(final List<String> imageUrls) {
        this.archiveImages = imageUrls.stream()
            .map(ArchiveImage::createArchiveImage)
            .collect(Collectors.toList());
    }
}
