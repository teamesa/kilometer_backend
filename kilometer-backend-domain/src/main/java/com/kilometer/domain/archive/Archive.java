package com.kilometer.domain.archive;

import com.kilometer.domain.archive.dto.ArchiveInfo;
import com.kilometer.domain.item.ItemEntity;
import com.kilometer.domain.user.User;
import java.time.LocalDateTime;
import java.util.List;
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
import org.springframework.data.annotation.CreatedDate;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "archive")
public class Archive {

    @Id @GeneratedValue
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

    //======= 연관관계 =======
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private ItemEntity item;

    @OneToMany(mappedBy = "archive")
    @Builder.Default
    private List<VisitedPlace> visitedPlaces = List.of();


    public ArchiveInfo makeInfo() {
        String food = null;
        String cafe = null;

        for (VisitedPlace place : visitedPlaces) {
            if(PlaceType.CAFE == place.getPlaceType()) {
                cafe = place.getPlaceName();
            } else {
                food = place.getPlaceName();
            }
        }

        return ArchiveInfo.builder()
            .userProfileUrl(this.user.getImageUrl())
            .userName(this.user.getName())
            .createdAt(this.createdAt)
            .startRating(this.starRating)
            .heartCount(this.heartCount)
            .isHearted(false)
            .comment(this.comment)
            .food(food)
            .cafe(cafe)
            .build();
    }

    public void setVisitedPlaces(List<VisitedPlace> places) {
        this.visitedPlaces = places;
        this.visitedPlaces.forEach(place -> place.setArchive(this));
    }

}
