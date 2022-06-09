package com.kilometer.domain.archive.dto;

import com.kilometer.domain.archive.VisitedPlace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PlaceInfo {
    private String placeType;
    private String name;
    private String address;
    private String roadAddress;

    public static PlaceInfo makePlaceInfo(VisitedPlace visitedPlace) {
        return PlaceInfo.builder()
                .placeType(String.valueOf(visitedPlace.getPlaceType()))
                .name(visitedPlace.getPlaceName())
                .address(visitedPlace.getAddress())
                .roadAddress(visitedPlace.getRoadAddress())
                .build();
    }
}
