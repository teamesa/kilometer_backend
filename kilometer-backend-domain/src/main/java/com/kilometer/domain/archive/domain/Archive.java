package com.kilometer.domain.archive.domain;

import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.domain.archiveimages.ArchiveImages;
import com.kilometer.domain.archive.dto.PlaceInfo;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.user.User;
import java.util.List;
import java.util.stream.Collectors;

public class Archive {

    private static final int MAX_STAR_RATING = 5;
    private static final int MIN_STAR_RATING = 1;

    private Long id;
    private String comment;
    private int starRating;
    private boolean isVisibleAtItem;
    private ArchiveImages archiveImages;
    private List<UserVisitPlace> userVisitPlaces;

    private Archive(final Long id, final String comment, final int starRating, final boolean isVisibleAtItem,
                    final List<String> photoUrls, final List<PlaceInfo> placeInfos) {
        validate(comment, starRating);
        this.comment = comment;
        this.starRating = starRating;
        this.isVisibleAtItem = isVisibleAtItem;
        this.archiveImages = new ArchiveImages(photoUrls);
        this.userVisitPlaces = placeInfos.stream()
                .map(UserVisitPlace::createUserVisitPlace)
                .collect(Collectors.toList());
    }

    private void validate(final String comment, final int starRating) {
        validateCommentField(comment);
        validateStarRatingField(starRating);
    }

    private void validateCommentField(final String comment) {
        if (comment == null) {
            throw new ArchiveValidationException();
        }
    }

    private void validateStarRatingField(final int starRating) {
        if (starRating < MIN_STAR_RATING || starRating > MAX_STAR_RATING) {
            throw new ArchiveValidationException();
        }
    }

    public static Archive createArchive(final String comment, final int starRating, final boolean isVisibleAtItem,
                                        final List<String> photoUrls, final List<PlaceInfo> placeInfos) {
        return new Archive(null, comment, starRating, isVisibleAtItem, photoUrls, placeInfos);
    }

    public ArchiveEntity toArchiveEntity(final User user, final ItemEntity item) {
        return ArchiveEntity.builder()
                .comment(this.comment)
                .starRating(this.starRating)
                .isVisibleAtItem(this.isVisibleAtItem)
                .user(user)
                .item(item)
                .build();
    }

    public List<ArchiveImageEntity> createArchiveImageEntities() {
        return archiveImages.toEntity();
    }

    public List<UserVisitPlaceEntity> createUserVisitPlaceEntities() {
        return userVisitPlaces.stream()
                .map(UserVisitPlace::toEntity)
                .collect(Collectors.toList());
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

    public boolean getIsVisibleAtItem() {
        return isVisibleAtItem;
    }
}
