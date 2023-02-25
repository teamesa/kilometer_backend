package com.kilometer.domain.archive.request;

import com.kilometer.domain.archive.domain.Archive;
import com.kilometer.domain.archive.domain.ArchiveImage;
import com.kilometer.domain.archive.domain.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.archive.dto.PlaceInfo;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class ArchiveCreateRequest {

    private Long itemId;
    private String comment;
    private int starRating;
    private boolean isVisibleAtItem;
    private List<String> photoUrls;
    private List<PlaceInfo> placeInfos;

    public Archive toDomain() {
        return Archive.createArchive(
            this.comment, this.starRating, this.isVisibleAtItem, toArchiveImages(), toUserVisitPlace());
    }

    public List<ArchiveImage> toArchiveImages() {
        return this.photoUrls.stream()
            .map(ArchiveImage::createArchiveImage)
            .collect(Collectors.toList());
    }

    public List<UserVisitPlace> toUserVisitPlace() {
        return this.placeInfos.stream()
            .map(PlaceInfo::toDomain)
            .collect(Collectors.toList());
    }
}
