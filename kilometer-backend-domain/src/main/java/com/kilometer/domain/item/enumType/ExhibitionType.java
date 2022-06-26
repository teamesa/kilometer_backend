package com.kilometer.domain.item.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExhibitionType {

    ALL("모두"), EXHIBITION("전시회"), MUSICAL("뮤지컬"), FESTIVAL("뮤직페스티벌"), CONCERT("콘서트");

    private final String description;
}
