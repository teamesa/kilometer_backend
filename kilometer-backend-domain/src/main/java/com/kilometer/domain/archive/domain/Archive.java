package com.kilometer.domain.archive.domain;

import com.kilometer.domain.archive.domain.archiveFilter.ArchiveFilter;
import com.kilometer.domain.archive.domain.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import java.util.List;
import lombok.Getter;

@Getter
public class Archive {

    private static final int MAX_STAR_RATING = 5;
    private static final int MIN_STAR_RATING = 1;

    private final String comment;
    private final int starRating;
    private final boolean isVisibleAtItem;

    private final List<ArchiveImage> archiveImages;
    private final List<UserVisitPlace> userVisitPlaces;

    public Archive(final String comment, final int starRating, final boolean isVisibleAtItem,
                   final List<ArchiveImage> archiveImages, final List<UserVisitPlace> userVisitPlaces) {
        validateComment(comment);
        validateStarRating(starRating);

        this.comment = comment;
        this.starRating = starRating;
        this.isVisibleAtItem = isVisibleAtItem;
        this.archiveImages = archiveImages;
        this.userVisitPlaces = userVisitPlaces;
    }

    private void validateComment(final String comment) {
        ArchiveFilter archiveFilter = new ArchiveFilter();
        if (comment == null) {
            throw new ArchiveValidationException("입력된 comment가 없습니다.");
        }
        if (archiveFilter.isMatchWithForbiddenWords(comment)) {
            throw new ArchiveValidationException("입력된 comment에 금칙어가 포함되어 있습니다.");
        }
    }

    private void validateStarRating(final int starRating) {
        if (starRating < MIN_STAR_RATING || starRating > MAX_STAR_RATING) {
            throw new ArchiveValidationException("별점은 0~5 사이의 양수이어야 합니다.");
        }
    }
}
