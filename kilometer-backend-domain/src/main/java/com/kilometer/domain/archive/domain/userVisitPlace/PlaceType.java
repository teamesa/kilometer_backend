package com.kilometer.domain.archive.domain.userVisitPlace;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import java.util.Arrays;

public enum PlaceType {

    FOOD("FOOD"),
    CAFE("CAFE");

    private final String name;

    PlaceType(final String name) {
        this.name = name;
    }

    public static PlaceType findBy(final String name) {
        return Arrays.stream(PlaceType.values())
            .filter(value -> value.name.equals(name))
            .findAny()
            .orElseThrow(() -> new ArchiveValidationException("일치하는 방문 장소 종류가 없습니다."));
    }

    public String getName() {
        return this.name;
    }
}
