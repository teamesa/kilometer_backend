package com.kilometer.domain.archive.domain.archiveImages;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArchiveImagesTest {

    @Test
    @DisplayName("ArchiveImages를 생성한다.")
    void createArchiveImages() {
        // given
        List<String> imageUrls = List.of();

        // when
        ArchiveImages actual = new ArchiveImages(imageUrls);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("ArchiveImages를 생성 할 때, archiveImages가 null이면 예외가 발생한다.")
    void createArchiveImages_nullException() {
        // given
        List<String> invalidImageUrls = null;

        // when & then
        assertThatThrownBy(() -> new ArchiveImages(invalidImageUrls))
                .isInstanceOf(ArchiveValidationException.class)
                .hasMessage("입력된 전시회 사진 URL이 없습니다.");
    }

    @Test
    @DisplayName("ArchiveImages를 Entity 객체로 변환한다.")
    void convertToEntity() {
        // given
        ArchiveImages archiveImages = new ArchiveImages(List.of("dummy"));

        // when
        List<ArchiveImageEntity> actual = archiveImages.toEntity();

        // then
        assertAll(
                () -> assertThat(actual).hasSize(1)
        );
    }
}
