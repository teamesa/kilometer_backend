package com.kilometer.domain.archive.domain.archiveImages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArchiveImageTest {

    @Test
    @DisplayName("ArchiveImage를 생성한다.")
    void createArchiveImage() {
        // given & when
        ArchiveImage actual = ArchiveImage.createArchiveImage("imageUrl");

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("ArchiveImage의 imageUrl이 null이면 예외가 발생한다.")
    void imageUrlIsNotNull() {
        // given
        String invalidImageUrl = null;

        // when
        ArchiveImage actual = ArchiveImage.createArchiveImage(invalidImageUrl);

        // when & then
        assertThatThrownBy(actual::validateImageUrlField)
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage("이미지 링크가 없습니다.");
    }
}
