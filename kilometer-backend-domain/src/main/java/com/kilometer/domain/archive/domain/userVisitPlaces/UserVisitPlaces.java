package com.kilometer.domain.archive.domain.userVisitPlaces;

import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
import java.util.List;
import java.util.stream.Collectors;

public class UserVisitPlaces {

    private final List<UserVisitPlace> userVisitPlaces;

    public UserVisitPlaces(final List<UserVisitPlace> userVisitPlaces) {
        this.userVisitPlaces = userVisitPlaces;
    }

    public List<UserVisitPlaceEntity> toEntity() {
        return userVisitPlaces.stream()
                .map(UserVisitPlace::toEntity)
                .collect(Collectors.toList());
    }
}
