package com.kilometer.domain.archive.dto;

import com.kilometer.domain.archive.domain.userVisitPlace.UserVisitPlace;
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
        return new UserVisitPlace(null, this.placeType, this.name, this.address, this.roadAddress);
    }
}
