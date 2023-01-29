package com.kilometer.domain.archive.dto;

import com.kilometer.domain.archive.domain.userVisitPlaces.UserVisitPlace;
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

    public UserVisitPlace toDomain() {
        return new UserVisitPlace(null, placeType, name, address, roadAddress);
    }
}
