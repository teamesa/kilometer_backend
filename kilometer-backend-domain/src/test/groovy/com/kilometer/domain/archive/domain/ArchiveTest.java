package com.kilometer.domain.archive.domain;

import static com.kilometer.common.statics.Statics.아카이브_공개_설정;
import static com.kilometer.common.statics.Statics.아카이브_별정;
import static com.kilometer.common.statics.Statics.아카이브_코멘트;
import static com.kilometer.common.statics.Statics.전시회_ID;
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
        Archive archive = new Archive(전시회_ID, 아카이브_코멘트, 아카이브_별정, 아카이브_공개_설정);

        // then
        assertThat(archive).isNotNull();
    }

    @Test
    @DisplayName("Archive의 comment 가 null이면 예외가 발생한다.")
    void commentIsNotNull() {
        // given
        String invalidComment = null;

        Archive archive = new Archive(전시회_ID, invalidComment, 아카이브_별정, 아카이브_공개_설정);

        // when & then
        assertThatThrownBy(archive::validate)
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage("입력된 comment가 없습니다.");
    }

    @Test
    @DisplayName("Archive의 starRating이 음수면 예외가 발생한다.")
    void archiveStarRatingIsNotNegative() {
        // given
        int invalidStarRating = -1;
        Archive archive = new Archive(전시회_ID, 아카이브_코멘트, invalidStarRating, 아카이브_공개_설정);

        // when & then
        assertThatThrownBy(archive::validate)
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage("별점은 0~5 사이의 양수이어야 합니다.");
    }

    @Test
    @DisplayName("Archive의 starRating이 5보다 크면 예외가 발생한다.")
    void archiveStarRatingIsPositiveOutOfRange() {
        // given
        int invalidStarRating = 6;
        Archive archive = new Archive(전시회_ID, 아카이브_코멘트, invalidStarRating, 아카이브_공개_설정);

        // when & then
        assertThatThrownBy(archive::validate)
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage("별점은 0~5 사이의 양수이어야 합니다.");
    }
}
