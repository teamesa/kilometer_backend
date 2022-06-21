package com.kilometer.domain.archive.dto;

import com.kilometer.domain.archive.userVisitPlace.UserVisitPlace;
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

    public static PlaceInfo makePlaceInfo(UserVisitPlace userVisitPlace) {
        return PlaceInfo.builder()
            .placeType(String.valueOf(userVisitPlace.getPlaceType()))
            .name(userVisitPlace.getPlaceName())
            .address(userVisitPlace.getAddress())
            .roadAddress(userVisitPlace.getRoadAddress())
            .build();
    }
}
