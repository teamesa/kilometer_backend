package com.kilometer.domain.archive.request;

import com.kilometer.domain.archive.Archive;
import com.kilometer.domain.archive.PlaceType;
import com.kilometer.domain.archive.archiveImage.ArchiveImage;
import com.kilometer.domain.archive.dto.PlaceInfo;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlace;
import java.util.ArrayList;
import java.util.List;
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

    public Archive makeArchive() {
        return Archive.builder()
            .comment(this.getComment())
            .starRating(this.getStarRating())
            .isVisibleAtItem(this.isVisibleAtItem())
            .build();
    }


    public List<ArchiveImage> makeArchiveImages() {
        List<ArchiveImage> images = new ArrayList<>();
        this.getPhotoUrls().forEach(url -> {
            ArchiveImage photo = ArchiveImage.builder().imageUrl(url).build();
            images.add(photo);
        });
        return images;
    }

    public List<UserVisitPlace> makeVisitedPlace() {
        List<UserVisitPlace> places = new ArrayList<>();
        for (PlaceInfo info : this.getPlaceInfos()) {
            UserVisitPlace visitPlace = UserVisitPlace.builder()
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
