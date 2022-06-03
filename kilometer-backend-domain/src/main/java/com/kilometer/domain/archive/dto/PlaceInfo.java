package com.kilometer.domain.archive.dto;

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
}
