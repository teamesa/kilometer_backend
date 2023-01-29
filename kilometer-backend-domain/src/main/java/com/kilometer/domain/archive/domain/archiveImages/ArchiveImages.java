package com.kilometer.domain.archive.domain.archiveImages;

import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import java.util.List;
import java.util.stream.Collectors;

public class ArchiveImages {

    private final List<ArchiveImage> archiveImages;

    public ArchiveImages(final List<String> photoUrls) {
        validate(photoUrls);
        this.archiveImages = photoUrls.stream()
                .map(ArchiveImage::createArchiveImage)
                .collect(Collectors.toList());
    }

    private void validate(final List<String> photoUrls) {
        if (photoUrls == null) {
            throw new ArchiveValidationException("입력된 전시회 사진 URL이 없습니다.");
        }
    }

    public List<ArchiveImageEntity> toEntity() {
        return archiveImages.stream()
                .map(ArchiveImage::toEntity)
                .collect(Collectors.toList());
    }
}
