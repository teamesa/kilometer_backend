package com.kilometer.domain.archive.domain;

import com.kilometer.domain.archive.domain.archiveFilter.ArchiveFilter;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import lombok.Getter;

@Getter
public class Archive {

    private static final int MAX_STAR_RATING = 5;
    private static final int MIN_STAR_RATING = 1;

    private final Long id;
    private final String comment;
    private final int starRating;
    private final boolean isVisibleAtItem;

    public Archive(final Long id, final String comment, final int starRating, final boolean isVisibleAtItem) {
        this.id = id;
        this.comment = comment;
        this.starRating = starRating;
        this.isVisibleAtItem = isVisibleAtItem;
    }

    public void validate() {
        validateCommentField();
        validateStarRatingField();
    }

    private void validateCommentField() {
        ArchiveFilter archiveFilter = new ArchiveFilter();
        if (this.comment == null) {
            throw new ArchiveValidationException("입력된 comment가 없습니다.");
        }
        if (archiveFilter.isMatchWithForbiddenWords(this.comment)) {
            throw new ArchiveValidationException("입력된 comment에 금칙어가 포함되어 있습니다.");
        }
    }

    private void validateStarRatingField() {
        if (this.starRating < MIN_STAR_RATING || this.starRating > MAX_STAR_RATING) {
            throw new ArchiveValidationException("별점은 0~5 사이의 양수이어야 합니다.");
        }
    }
}
