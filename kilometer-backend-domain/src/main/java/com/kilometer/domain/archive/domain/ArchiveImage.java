package com.kilometer.domain.archive.domain;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import lombok.Getter;
import org.junit.platform.commons.util.StringUtils;

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
        if (StringUtils.isBlank(imageUrl)) {
            throw new ArchiveValidationException("이미지 링크가 없습니다.");
        }
    }
}
