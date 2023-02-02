package com.kilometer.domain.archive.domain.userVisitPlace;

import com.kilometer.domain.archive.PlaceType;

public class UserVisitPlace {

    private Long id;
    private PlaceType placeType;
    private String placeName;
    private String address;
    private String roadAddress;

    public UserVisitPlace(final Long id, final String placeType, final String placeName, final String address,
                          final String roadAddress) {
        this.id = id;
        this.placeType = PlaceType.valueOf(placeType);
        this.placeName = placeName;
        this.address = address;
        this.roadAddress = roadAddress;
    }
}
