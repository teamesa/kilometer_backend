package com.kilometer.domain.archive.domain;

import static com.kilometer.common.statics.Statics.아카이브_공개_설정;
import static com.kilometer.common.statics.Statics.아카이브_별점;
import static com.kilometer.common.statics.Statics.아카이브_코멘트;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kilometer.domain.archive.domain.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArchiveTest {

    private final List<ArchiveImage> 전시회_사진들 = List.of();
    private final List<UserVisitPlace> 근처_맛집_사진들 = List.of();

    @Test
    @DisplayName("Archive를 생성한다.")
    void createArchive() {
        // given & when
        Archive archive = Archive.createArchive(아카이브_코멘트, 아카이브_별점, 아카이브_공개_설정, 전시회_사진들, 근처_맛집_사진들);

        // then
        assertThat(archive).isNotNull();
    }

    @Test
    @DisplayName("Archive의 comment 가 null이면 예외가 발생한다.")
    void commentIsNotNull() {
        // given
        String invalidComment = null;

        // when & then
        assertThatThrownBy(() -> Archive.createArchive(invalidComment, 아카이브_별점, 아카이브_공개_설정, 전시회_사진들, 근처_맛집_사진들))
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage("입력된 comment가 없습니다.");
    }

    @Test
    @DisplayName("Archive의 starRating이 음수면 예외가 발생한다.")
    void archiveStarRatingIsNotNegative() {
        // given
        int invalidStarRating = -1;

        // when & then
        assertThatThrownBy(() -> Archive.createArchive(아카이브_코멘트, invalidStarRating, 아카이브_공개_설정, 전시회_사진들, 근처_맛집_사진들))
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage("별점은 0~5 사이의 양수이어야 합니다.");
    }

    @Test
    @DisplayName("Archive의 starRating이 5보다 크면 예외가 발생한다.")
    void archiveStarRatingIsPositiveOutOfRange() {
        // given
        int invalidStarRating = 6;

        // when & then
        assertThatThrownBy(() -> Archive.createArchive(아카이브_코멘트, invalidStarRating, 아카이브_공개_설정, 전시회_사진들, 근처_맛집_사진들))
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage("별점은 0~5 사이의 양수이어야 합니다.");
    }

    @Test
    @DisplayName("Archive의 comment에 금칙어가 포함되면 예외가 발생한다.")
    void validateCommentField() {
        // given
        String 금칙어가_포함된_코멘트 = "이건캐놈입니다.";

        // when & then
        assertThatThrownBy(() -> Archive.createArchive(금칙어가_포함된_코멘트, 아카이브_별점, 아카이브_공개_설정, 전시회_사진들, 근처_맛집_사진들))
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage("입력된 comment에 금칙어가 포함되어 있습니다.");
    }
}
