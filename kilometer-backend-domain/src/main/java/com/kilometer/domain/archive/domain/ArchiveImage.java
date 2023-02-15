package com.kilometer.domain.archive.domain;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import lombok.Getter;

@Getter
public class ArchiveImage {

    private final String imageUrl;

    public ArchiveImage(final String imageUrl) {
        validateImageUrl(imageUrl);
        this.imageUrl = imageUrl;
    }

    private void validateImageUrl(final String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new ArchiveValidationException("이미지 링크가 없습니다.");
        }
    }
}
