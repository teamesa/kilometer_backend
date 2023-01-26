package com.kilometer.domain.archive.domain;

import com.kilometer.domain.archive.exception.ArchiveValidationException;

public class Archive {

    private static final int MAX_STAR_RATING = 5;
    private static final int MIN_STAR_RATING = 1;
    private static final int MIN_LIKE_COUNT = 0;

    private Long id;
    private String comment;
    private int starRating;
    private int likeCount;
    private boolean isVisibleAtItem;

    public Archive(final Long id, final String comment, final int starRating, final int likeCount,
                   final boolean isVisibleAtItem) {
        validate(comment, starRating, likeCount);
        this.id = id;
        this.comment = comment;
        this.starRating = starRating;
        this.likeCount = likeCount;
        this.isVisibleAtItem = isVisibleAtItem;
    }

    public Archive(final String comment, final int starRating, final int likeCount,
                   final boolean isVisibleAtItem) {
        this(null, comment, starRating, likeCount, isVisibleAtItem);
    }

    private void validate(final String comment, final int starRating, final int likeCount) {
        validateCommentField(comment);
        validateStarRatingField(starRating);
        validateLikeCount(likeCount);
    }

    private void validateCommentField(final String comment) {
        if (comment == null) {
            throw new ArchiveValidationException("코멘트");
        }
    }

    private void validateStarRatingField(final int starRating) {
        if (starRating < MIN_STAR_RATING || starRating > MAX_STAR_RATING) {
            throw new ArchiveValidationException();
        }
    }

    private void validateLikeCount(final int likeCount) {
        if (likeCount < MIN_LIKE_COUNT) {
            throw new ArchiveValidationException();
        }
    }

    public Long getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public int getStarRating() {
        return starRating;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public boolean getIsVisibleAtItem() {
        return isVisibleAtItem;
    }
}
