package com.kilometer.domain.archive.domain.userVisitPlace;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import java.util.Arrays;

public enum PlaceType {

    FOOD("맛집"),
    CAFE("카페");

    private final String name;

    PlaceType(final String name) {
        this.name = name;
    }

    public static PlaceType findByName(final String name) {
        return Arrays.stream(values())
            .filter(value -> value.name.equals(name))
            .findAny()
            .orElseThrow(() -> new ArchiveValidationException("일치하는 방문 장소 종류가 없습니다."));
    }

    public String getName() {
        return this.name;
    }
}
