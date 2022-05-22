package com.kilometer.domain.item;

public enum ExposureType {

    ON("전시"), OFF("미전시");

    private final String description;

    ExposureType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
