package com.kilometer.domain.item;

public enum ExhibitionType {

    EXHIBITION("전시회"), MUSICAL("뮤지컬"), FESTIVAL("뮤직페스티벌"), CONCERT("콘서트");

    private final String description;

    ExhibitionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
