package com.kilometer.domain.archive.domain.userVisitPlace;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import java.util.List;

public class UserVisitPlaces {

    private final List<UserVisitPlace> userVisitPlaces;

    public UserVisitPlaces(final List<UserVisitPlace> userVisitPlaces) {
        this.userVisitPlaces = userVisitPlaces;
    }

    public void validate() {
        if (this.userVisitPlaces == null) {
            throw new ArchiveValidationException("입력된 방문 장소가 없습니다.");
        }
    }
}
