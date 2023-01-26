package com.kilometer.domain.archive.domain;

public class Archive {

    private Long id;
    private String comment;
    private int starRating;
    private int likeCount;
    private boolean isVisibleAtItem;

    public Archive(final Long id, final String comment, final int starRating, final int likeCount,
                   final boolean isVisibleAtItem) {
        this.id = id;
        this.comment = comment;
        this.starRating = starRating;
        this.likeCount = likeCount;
        this.isVisibleAtItem = isVisibleAtItem;
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

    public boolean isVisibleAtItem() {
        return isVisibleAtItem;
    }
}
