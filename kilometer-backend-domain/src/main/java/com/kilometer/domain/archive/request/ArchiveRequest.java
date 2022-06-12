package com.kilometer.domain.archive.request;

import com.kilometer.domain.archive.dto.PlaceInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ArchiveRequest {

    private Long itemId;
    private String comment;
    private int starRating;
    private boolean isVisibleAtItem;
    private List<String> photoUrls;
    private List<PlaceInfo> placeInfos;

}
