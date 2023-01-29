package com.kilometer.domain.archive.request;

import com.kilometer.domain.archive.PlaceType;
import com.kilometer.domain.archive.archiveImage.ArchiveImageEntity;
import com.kilometer.domain.archive.domain.Archive;
import com.kilometer.domain.archive.domain.userVisitPlaces.UserVisitPlace;
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
public class ArchiveRequest {

    private Long itemId;
    private String comment;
    private int starRating;
    private boolean isVisibleAtItem;
    private List<String> photoUrls;
    private List<PlaceInfo> placeInfos;

    public Archive toDomain() {
        return Archive.create(this.comment, this.starRating, this.isVisibleAtItem, this.photoUrls,
                toUserVisitPlace());
    }

    public List<ArchiveImageEntity> makeArchiveImages() {
        List<ArchiveImageEntity> images = new ArrayList<>();
        this.getPhotoUrls().forEach(url -> {
            ArchiveImageEntity photo = ArchiveImageEntity.builder().imageUrl(url).build();
            images.add(photo);
        });
        return images;
    }

    private List<UserVisitPlace> toUserVisitPlace() {
        return placeInfos.stream()
                .map(placeInfo -> placeInfo.toDomain())
                .collect(Collectors.toList());
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
}
