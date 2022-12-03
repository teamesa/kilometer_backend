package com.kilometer.domain.user;

import java.util.Arrays;

public enum Gender {
    FEMALE("F"),
    MALE("M"),
    UNKNOWN("UNKNOWN");

    private final String genderSymbol;

    Gender(String genderSymbol) {
        this.genderSymbol = genderSymbol;
    }

    public static Gender from(String genderSymbol) {
        return Arrays.stream(values())
                .filter(gender -> gender.genderSymbol.equals(genderSymbol))
                .findFirst()
                .orElse(UNKNOWN);
    }
}
