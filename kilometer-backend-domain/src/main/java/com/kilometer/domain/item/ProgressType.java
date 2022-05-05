package com.kilometer.domain.item;

public enum ProgressType {

    ON("전시"), OFF("미전시");

    private final String description;

    ProgressType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
