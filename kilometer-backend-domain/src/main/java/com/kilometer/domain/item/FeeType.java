package com.kilometer.domain.item;

public enum FeeType {

    FREE("무료"), COST("유료");

    private final String description;

    FeeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
