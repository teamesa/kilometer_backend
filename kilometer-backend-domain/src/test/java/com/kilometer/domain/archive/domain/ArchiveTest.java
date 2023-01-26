package com.kilometer.domain.archive.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArchiveTest {

    @Test
    @DisplayName("Archive를 생성한다.")
    void createArchive() {
        // given & when
        Archive archive = new Archive("comment", 1, 0, true);

        // then
        assertThat(archive).isNotNull();
    }

    @Test
    @DisplayName("Archive의 comment 가 null이면 예외가 발생한다.")
    void commentIsNotNull() {
        // given
        String invalidComment = null;

        // when & then
        assertThatThrownBy(() -> new Archive(invalidComment, 1, 0, true))
                .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("Archive의 starRating이 음수면 예외가 발생한다.")
    void archiveStarRatingIsNotNegative() {
        // given
        int invalidStarRating = -1;

        // when & then
        assertThatThrownBy(() -> new Archive("김철수책상철책상", invalidStarRating, 0, true))
                .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("Archive의 starRating이 5보다 크면 예외가 발생한다.")
    void archiveStarRatingIsPositiveOutOfRange() {
        // given
        int invalidStarRating = 6;

        // when & then
        assertThatThrownBy(() -> new Archive("김철수책상철책상", invalidStarRating, 0, true))
                .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("Archive의 like Count이 음수면 예외가 발생한다.")
    void archiveStarRatingIsOutOfRange() {
        // given
        int invalidLikeCount = -1;

        // when & then
        assertThatThrownBy(() -> new Archive("김철수책상철책상", 1, invalidLikeCount, true))
                .isInstanceOf(ArchiveValidationException.class);
    }
}