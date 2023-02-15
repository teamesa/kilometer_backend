package com.kilometer.domain.archive.domain.userVisitPlace;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import lombok.Getter;

@Getter
public class UserVisitPlace {

    private final PlaceType placeType;
    private final String placeName;
    private final String address;
    private final String roadAddress;

    public UserVisitPlace(final String placeType, final String placeName, final String address,
                          final String roadAddress) {
        validate(placeName, address, roadAddress);

        this.placeType = PlaceType.findByName(placeType);
        this.placeName = placeName;
        this.address = address;
        this.roadAddress = roadAddress;
    }

    private void validate(final String placeName, final String address, final String roadAddress) {
        if (placeName == null || placeName.isBlank()) {
            throw new ArchiveValidationException("입력된 장소명이 없습니다.");
        }
        if (address == null || address.isBlank()) {
            throw new ArchiveValidationException("입력된 지번 주소가 없습니다.");
        }
        if (roadAddress == null || roadAddress.isBlank()) {
            throw new ArchiveValidationException("입력된 도로명 주소가 없습니다.");
        }
    }
}
