package com.kilometer.domain.archive.domain.userVisitPlace;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import lombok.Getter;
import org.junit.platform.commons.util.StringUtils;

@Getter
public class UserVisitPlace {

    private final PlaceType placeType;
    private final String placeName;
    private final String address;
    private final String roadAddress;

    private UserVisitPlace(final PlaceType placeType, final String placeName, final String address,
                           final String roadAddress) {
        this.placeType = placeType;
        this.placeName = placeName;
        this.address = address;
        this.roadAddress = roadAddress;
    }

    public static UserVisitPlace createUserVisitPlace(final String placeType, final String placeName,
                                                      final String address, final String roadAddress) {
        validate(placeName, address, roadAddress);
        return new UserVisitPlace(PlaceType.valueOf(placeType), placeName, address, roadAddress);
    }

    private static void validate(final String placeName, final String address, final String roadAddress) {
        if (StringUtils.isBlank(placeName)) {
            throw new ArchiveValidationException("입력된 장소명이 없습니다.");
        }
        if (StringUtils.isBlank(address)) {
            throw new ArchiveValidationException("입력된 지번 주소가 없습니다.");
        }
        if (StringUtils.isBlank(roadAddress)) {
            throw new ArchiveValidationException("입력된 도로명 주소가 없습니다.");
        }
    }
}
