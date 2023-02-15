package com.kilometer.domain.archive.domain.userVisitPlace;

public enum PlaceType {

    FOOD("맛집"),
    CAFE("카페");

    private final String name;

    PlaceType(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
