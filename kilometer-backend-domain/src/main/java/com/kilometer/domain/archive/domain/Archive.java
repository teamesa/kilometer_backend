package com.kilometer.domain.archive.domain;

import com.kilometer.domain.archive.domain.archiveImages.ArchiveImages;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import java.util.List;
import lombok.Getter;

@Getter
public class Archive {

    private static final int MAX_STAR_RATING = 5;
    private static final int MIN_STAR_RATING = 1;

    private final Long id;
    private final String comment;
    private final int starRating;
    private final boolean isVisibleAtItem;
    private final ArchiveImages archiveImages;

    public Archive(final Long id, final String comment, final int starRating, final boolean isVisibleAtItem,
                   final List<String> imageUrls) {
        this.id = id;
        this.comment = comment;
        this.starRating = starRating;
        this.isVisibleAtItem = isVisibleAtItem;
        this.archiveImages = new ArchiveImages(imageUrls);
    }

    public void validate() {
        validateCommentField(comment);
        validateStarRatingField(starRating);
    }

    private void validateCommentField(final String comment) {
        if (comment == null) {
            throw new ArchiveValidationException("입력된 comment가 없습니다.");
        }
    }

    private void validateStarRatingField(final int starRating) {
        if (starRating < MIN_STAR_RATING || starRating > MAX_STAR_RATING) {
            throw new ArchiveValidationException("별점은 0~5 사이의 양수이어야 합니다.");
        }
    }
}
