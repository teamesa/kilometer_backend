package com.kilometer.domain.archive.domain;

import com.kilometer.domain.archive.PlaceType;
import com.kilometer.domain.archive.dto.PlaceInfo;

public class UserVisitPlace {

    private Long id;
    private PlaceType placeType;
    private String placeName;
    private String address;
    private String roadAddress;

    private UserVisitPlace(final Long id, final String placeType, final String placeName, final String address,
                           final String roadAddress) {
        this.id = id;
        this.placeType = PlaceType.valueOf(placeType);
        this.placeName = placeName;
        this.address = address;
        this.roadAddress = roadAddress;
    }

    public static UserVisitPlace createUserVisitPlace(final PlaceInfo placeInfo) {
        return new UserVisitPlace(null, placeInfo.getPlaceType(), placeInfo.getName(), placeInfo.getAddress(),
                placeInfo.getRoadAddress());
    }
}
