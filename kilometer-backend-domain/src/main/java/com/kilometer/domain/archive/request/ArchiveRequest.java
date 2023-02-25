package com.kilometer.domain.archive.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kilometer.domain.archive.ArchiveEntity;
import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.domain.Archive;
import com.kilometer.domain.archive.domain.ArchiveImage;
import com.kilometer.domain.archive.domain.userVisitPlace.PlaceType;
import com.kilometer.domain.archive.domain.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.archive.dto.PlaceInfo;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArchiveRequest {

    private Long itemId;
    private String comment;
    private int starRating;
    private boolean isVisibleAtItem;
    private List<String> photoUrls;
    private List<PlaceInfo> placeInfos;

    public ArchiveEntity makeArchive() {
        return ArchiveEntity.builder()
            .comment(this.getComment())
            .starRating(this.getStarRating())
            .isVisibleAtItem(this.isVisibleAtItem())
            .build();
    }


    public List<ArchiveImageEntity> makeArchiveImages() {
        List<ArchiveImageEntity> images = new ArrayList<>();
        this.getPhotoUrls().forEach(url -> {
            ArchiveImageEntity photo = ArchiveImageEntity.builder().imageUrl(url).build();
            images.add(photo);
        });
        return images;
    }

    public List<UserVisitPlaceEntity> makeVisitedPlace() {
        List<UserVisitPlaceEntity> places = new ArrayList<>();
        for (PlaceInfo info : this.getPlaceInfos()) {
            UserVisitPlaceEntity visitPlace = UserVisitPlaceEntity.builder()
                .placeType(PlaceType.valueOf(info.getPlaceType()))
                .placeName(info.getName())
                .address(info.getAddress())
                .roadAddress(info.getRoadAddress())
                .build();
            places.add(visitPlace);
        }
        return places;
    }

    public Archive toDomain() {
        return Archive.createArchive(
            this.comment, this.starRating, this.isVisibleAtItem, archiveImages(), userVisitPlace());
    }

    public List<ArchiveImage> archiveImages() {
        return this.photoUrls.stream()
            .map(ArchiveImage::createArchiveImage)
            .collect(Collectors.toList());
    }

    public List<UserVisitPlace> userVisitPlace() {
        return this.placeInfos.stream()
            .map(PlaceInfo::toDomain)
            .collect(Collectors.toList());
    }
}
