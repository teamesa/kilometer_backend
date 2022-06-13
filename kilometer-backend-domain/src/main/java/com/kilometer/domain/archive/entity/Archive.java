package com.kilometer.domain.archive.entity;

import com.kilometer.domain.archive.PlaceType;
import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.archive.request.ArchiveRequest;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.user.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "archive")
public class Archive {

    @Id
    @GeneratedValue
    private Long id;

    @Builder.Default
    @Column(length = 1000)
    private String comment = null;

    private int starRating;

    @Builder.Default
    private int heartCount = 0;

    @Builder.Default
    private boolean isVisibleAtItem = true;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Builder.Default
    private boolean isDeleted = false;

    //======= 연관관계 =======
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item")
    private ItemEntity item;

    @OneToMany(mappedBy = "archive", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<VisitedPlace> visitedPlaces = new ArrayList<>();

    @OneToMany(mappedBy = "archive", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ArchiveImage> archiveImages = new ArrayList<>();

    public ArchiveInfo makeInfo() {
        String food = "";
        String cafe = "";

        for (VisitedPlace place : visitedPlaces) {
            if (PlaceType.CAFE == place.getPlaceType()) {
                cafe = place.getPlaceName();
            } else {
                food = place.getPlaceName();
            }
        }

        return ArchiveInfo.builder()
            .id(this.id)
            .userProfileUrl(this.user.getImageUrl())
            .userName(this.user.getName())
            .updatedAt(this.updatedAt)
            .starRating(this.starRating)
            .heartCount(this.heartCount)
            .isHearted(false)
            .comment(this.comment)
            .food(food)
            .cafe(cafe)
            .build();
    }

    public void setVisitedPlaces(List<VisitedPlace> places) {
        if(!this.visitedPlaces.isEmpty()) {
            this.visitedPlaces.clear();
        }
        this.visitedPlaces.addAll(places);
        this.visitedPlaces.forEach(place -> place.setArchive(this));
    }

    public void setImages(List<ArchiveImage> images) {
        if(!this.archiveImages.isEmpty()) {
            this.archiveImages.clear();
        }
        this.archiveImages.addAll(images);
        this.archiveImages.forEach(photo -> photo.setArchive(this));
    }

    public void update(ArchiveRequest request) {
        this.comment = request.getComment();
        this.isVisibleAtItem = request.isVisibleAtItem();
        this.starRating = request.getStarRating();
    }
}
