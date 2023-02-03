package com.kilometer.domain.archive.dto;

import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceInfo {

    private String placeType;
    private String name;
    private String address;
    private String roadAddress;

    public static PlaceInfo makePlaceInfo(UserVisitPlaceEntity userVisitPlaceEntity) {
        return PlaceInfo.builder()
            .placeType(String.valueOf(userVisitPlaceEntity.getPlaceType()))
            .name(userVisitPlaceEntity.getPlaceName())
            .address(userVisitPlaceEntity.getAddress())
            .roadAddress(userVisitPlaceEntity.getRoadAddress())
            .build();
    }
}
