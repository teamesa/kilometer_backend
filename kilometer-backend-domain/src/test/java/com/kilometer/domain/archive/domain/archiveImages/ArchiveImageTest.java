package com.kilometer.domain.archive.domain.archiveImages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.domain.archiveImages.ArchiveImage;
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

        // when & then
        assertThatThrownBy(() -> ArchiveImage.createArchiveImage(invalidImageUrl))
                .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("ArchiveImage를 Entity 객체로 변환한다.")
    void convertToEntity() {
        // given
        ArchiveImage archiveImage = ArchiveImage.createArchiveImage("dummy");

        // when
        ArchiveImageEntity actual = archiveImage.toEntity();

        // then
        assertThat(actual.getImageUrl()).isEqualTo("dummy");
    }
}
