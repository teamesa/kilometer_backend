package com.kilometer.domain.archive.domain;

import static com.kilometer.common.statics.Statics.아카이브_이미지_URL;
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
        ArchiveImage actual = ArchiveImage.createArchiveImage(아카이브_이미지_URL);

        // then
        assertThat(actual).isInstanceOf(ArchiveImage.class);
    }

    @Test
    @DisplayName("ArchiveImage의 imageUrl이 null이면 예외가 발생한다.")
    void createArchiveImage_null() {
        // given
        String invalidImageUrl = null;

        // when & then
        assertThatThrownBy(() -> ArchiveImage.createArchiveImage(invalidImageUrl))
            .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("ArchiveImage의 imageUrl이 공백이면 예외가 발생한다.")
    void createArchiveImage_blank() {
        // given
        String invalidImageUrl = "   ";

        // when & then
        assertThatThrownBy(() -> ArchiveImage.createArchiveImage(invalidImageUrl))
            .isInstanceOf(ArchiveValidationException.class);
    }
}
