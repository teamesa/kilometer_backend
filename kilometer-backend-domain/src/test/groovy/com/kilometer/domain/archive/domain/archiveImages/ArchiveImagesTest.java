package com.kilometer.domain.archive.domain.archiveImages;

import static com.kilometer.common.statics.Statics.아카이브_이미지들;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArchiveImagesTest {

    @Test
    @DisplayName("ArchiveImages를 생성한다.")
    void createArchiveImages() {
        // given
        List<String> imageUrls = 아카이브_이미지들;

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
            .isInstanceOf(NullPointerException.class);
    }
}
