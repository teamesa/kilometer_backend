package com.kilometer.domain.homeModules.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ModuleType {
    KEY_VISUAL("key-visual"),
    SWIPE_ITEM("swipe-item"),
    MONTHLY_FREE_ITEM("monthly-free-item"),
    REAL_TIME_ARCHIVE("real-time-archive"),
    UPCOMING_ITEM("upcoming-item");

    private final String frontName;

    @Override
    public String toString() {
        return frontName;
    }
}
