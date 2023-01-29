package com.kilometer.domain.archive.request;

import com.kilometer.domain.archive.domain.Archive;
import com.kilometer.domain.archive.domain.userVisitPlaces.UserVisitPlace;
import com.kilometer.domain.archive.dto.PlaceInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArchiveCreateRequest {

    private Long itemId;
    private String comment;
    private int starRating;
    private boolean isVisibleAtItem;
    private List<String> photoUrls;
    private List<PlaceInfo> placeInfos;

    public Archive toDomain() {
        return new Archive(null, this.comment, this.starRating, this.isVisibleAtItem, this.photoUrls,
                toUserVisitPlace());
    }

    private List<UserVisitPlace> toUserVisitPlace() {
        return this.placeInfos.stream()
                .map(PlaceInfo::toDomain)
                .collect(Collectors.toList());
    }
}
