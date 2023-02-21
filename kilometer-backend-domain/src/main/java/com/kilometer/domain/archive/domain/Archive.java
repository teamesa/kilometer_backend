package com.kilometer.domain.archive.domain;

import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.domain.archiveFilter.ArchiveFilter;
import com.kilometer.domain.archive.domain.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class Archive {

    private static final int MAX_STAR_RATING = 5;
    private static final int MIN_STAR_RATING = 1;

    private final Long id;
    private final String comment;
    private final int starRating;
    private final boolean isVisibleAtItem;

    private final List<ArchiveImage> archiveImages;
    private final List<UserVisitPlace> userVisitPlaces;

    private Archive(final Long id, final String comment, final int starRating, final boolean isVisibleAtItem,
                    final List<ArchiveImage> archiveImages, final List<UserVisitPlace> userVisitPlaces) {
        this.id = id;
        this.comment = comment;
        this.starRating = starRating;
        this.isVisibleAtItem = isVisibleAtItem;
        this.archiveImages = new ArrayList<>(archiveImages);
        this.userVisitPlaces = new ArrayList<>(userVisitPlaces);
    }

    public static Archive createArchive(final String comment, final int starRating, final boolean isVisibleAtItem,
                                        final List<ArchiveImage> archiveImages,
                                        final List<UserVisitPlace> userVisitPlaces) {
        validateComment(comment);
        validateStarRating(starRating);
        return new Archive(null, comment, starRating, isVisibleAtItem, archiveImages, userVisitPlaces);
    }

    public static Archive createArchiveForUpdate(final Long id, final String comment, final int starRating,
                                                 final boolean isVisibleAtItem, final List<ArchiveImage> archiveImages,
                                                 final List<UserVisitPlace> userVisitPlaces) {
        validateId(id);
        validateComment(comment);
        validateStarRating(starRating);
        return new Archive(id, comment, starRating, isVisibleAtItem, archiveImages, userVisitPlaces);
    }

    private static void validateId(final Long id) {
        if (id == null) {
            throw new ArchiveValidationException("입력된 id가 없습니다.");
        }
    }

    private static void validateComment(final String comment) {
        if (comment == null) {
            throw new ArchiveValidationException("입력된 comment가 없습니다.");
        }
        if (ArchiveFilter.isMatchWithForbiddenWords(comment)) {
            throw new ArchiveValidationException("입력된 comment에 금칙어가 포함되어 있습니다.");
        }
    }

    private static void validateStarRating(final int starRating) {
        if (starRating < MIN_STAR_RATING || starRating > MAX_STAR_RATING) {
            throw new ArchiveValidationException("별점은 0~5 사이의 양수이어야 합니다.");
        }
    }

    public ArchiveEntity toEntity(final User user, final ItemEntity itemEntity) {
        return ArchiveEntity.builder()
            .comment(this.getComment())
            .starRating(this.getStarRating())
            .isVisibleAtItem(this.getIsVisibleAtItem())
            .user(user)
            .item(itemEntity)
            .build();
    }

    public List<ArchiveImageEntity> toArchiveImageEntities() {
        return this.archiveImages.stream()
            .map(ArchiveImage::toEntity)
            .collect(Collectors.toList());
    }

    public List<UserVisitPlaceEntity> createUserVisitPlaceEntities() {
        return this.userVisitPlaces.stream()
            .map(UserVisitPlace::toEntity)
            .collect(Collectors.toList());
    }

    public boolean getIsVisibleAtItem() {
        return isVisibleAtItem;
    }
}
