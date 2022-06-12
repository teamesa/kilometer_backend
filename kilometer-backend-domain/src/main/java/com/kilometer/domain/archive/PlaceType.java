package com.kilometer.domain.archive;

public enum PlaceType {
    FOOD("맛집"), CAFE("카페");

    private final String name;

    PlaceType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
