package com.kilometer.domain.item.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExposureType {

    ON("전시"), OFF("미전시");

    private final String description;
}
