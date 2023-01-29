package com.kilometer.domain.archive.domain.userVisitPlaces;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
import java.util.List;
import java.util.stream.Collectors;

public class UserVisitPlaces {

    private final List<UserVisitPlace> userVisitPlaces;

    public UserVisitPlaces(final List<UserVisitPlace> userVisitPlaces) {
        validate(userVisitPlaces);
        this.userVisitPlaces = userVisitPlaces;
    }

    private void validate(final List<UserVisitPlace> userVisitPlaces) {
        if (userVisitPlaces == null) {
            throw new ArchiveValidationException("입력된 방문 장소가 없습니다.");
        }
    }

    public List<UserVisitPlaceEntity> toEntity() {
        return userVisitPlaces.stream()
                .map(UserVisitPlace::toEntity)
                .collect(Collectors.toList());
    }
}
