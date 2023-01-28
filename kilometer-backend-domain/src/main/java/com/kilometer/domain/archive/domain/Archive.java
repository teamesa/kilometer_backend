package com.kilometer.domain.archive.domain;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import java.util.List;
import java.util.stream.Collectors;

public class Archive {

    private static final int DEFAULT_LIKE_COUNT = 0;
    private static final int MAX_STAR_RATING = 5;
    private static final int MIN_STAR_RATING = 1;

    private Long id;
    private String comment;
    private int starRating;
    private int likeCount;
    private boolean isVisibleAtItem;
    private List<ArchiveImage> archiveImages;

    private Archive(final Long id, final String comment, final int starRating, final int likeCount,
                    final boolean isVisibleAtItem, final List<String> photoUrls) {
        validate(comment, starRating, likeCount);
        this.comment = comment;
        this.starRating = starRating;
        this.likeCount = likeCount;
        this.isVisibleAtItem = isVisibleAtItem;
        this.archiveImages = photoUrls.stream()
                .map(ArchiveImage::createArchiveImage)
                .collect(Collectors.toList());
    }

    public static Archive createArchive(final String comment, final int starRating, final boolean isVisibleAtItem,
                                        final List<String> photoUrls) {
        return new Archive(null, comment, starRating, DEFAULT_LIKE_COUNT, isVisibleAtItem, photoUrls);
    }

    private void validate(final String comment, final int starRating, final int likeCount) {
        validateCommentField(comment);
        validateStarRatingField(starRating);
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
