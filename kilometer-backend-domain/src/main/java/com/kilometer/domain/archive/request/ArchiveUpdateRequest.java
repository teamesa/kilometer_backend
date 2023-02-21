package com.kilometer.domain.archive.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kilometer.domain.archive.domain.Archive;
import com.kilometer.domain.archive.domain.ArchiveImage;
import com.kilometer.domain.archive.domain.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.archive.dto.PlaceInfo;
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
public class ArchiveUpdateRequest {

    private Long itemId;
    private String comment;
    private int starRating;
    private boolean isVisibleAtItem;
    private List<String> photoUrls;
    private List<PlaceInfo> placeInfos;

    public Archive toDomain(final Long archiveId) {
        return Archive.createArchiveForUpdate(
            archiveId, this.comment, this.starRating, this.isVisibleAtItem, archiveImages(), userVisitPlace());
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
