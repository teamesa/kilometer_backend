package com.kilometer.domain.archive.domain.archiveImages;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import lombok.Getter;

@Getter
public class ArchiveImage {

    private Long id;
    private String imageUrl;

    private ArchiveImage(final Long id, final String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public static ArchiveImage createArchiveImage(final String imageUrl) {
        return new ArchiveImage(null, imageUrl);
    }

    public void validateImageUrlField() {
        if (this.imageUrl == null) {
            throw new ArchiveValidationException("이미지 링크가 없습니다.");
        }
    }
}