package com.kilometer.domain.archive.domain;

import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import lombok.Getter;

@Getter
public class ArchiveImage {

    private final String imageUrl;

    private ArchiveImage(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static ArchiveImage createArchiveImage(final String imageUrl) {
        validateImageUrl(imageUrl);
        return new ArchiveImage(imageUrl);
    }

    private static void validateImageUrl(final String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new ArchiveValidationException("이미지 링크가 없습니다.");
        }
    }

    public ArchiveImageEntity toEntity() {
        return ArchiveImageEntity.builder()
            .imageUrl(this.imageUrl)
            .build();
    }
}
