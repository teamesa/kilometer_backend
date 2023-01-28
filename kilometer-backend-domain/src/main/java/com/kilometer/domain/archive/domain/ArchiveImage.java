package com.kilometer.domain.archive.domain;

import com.kilometer.domain.archive.exception.ArchiveValidationException;

public class ArchiveImage {

    private Long id;
    private String imageUrl;

    private ArchiveImage(final Long id, final String imageUrl) {
        validate(imageUrl);
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public static ArchiveImage createArchiveImage(final String imageUrl) {
        return new ArchiveImage(null, imageUrl);
    }

    private void validate(final String imageUrl) {
        validateImageUrlField(imageUrl);
    }

    private void validateImageUrlField(final String imageUrl) {
        if (imageUrl == null) {
            throw new ArchiveValidationException("이미지 링크가 없습니다.");
        }
    }
}
