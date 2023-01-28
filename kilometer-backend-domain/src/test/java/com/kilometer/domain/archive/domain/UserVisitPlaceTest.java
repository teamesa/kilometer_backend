package com.kilometer.domain.archive.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.kilometer.domain.archive.dto.PlaceInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserVisitPlaceTest {

    @Test
    @DisplayName("UserVisitPlace를 생성한다.")
    void createUserVisitPlace() {
        // given
        PlaceInfo placeInfo = new PlaceInfo("CAFE", "cafe", "address", "roadAdderss");

        // when
        UserVisitPlace actual = UserVisitPlace.createUserVisitPlace(placeInfo);

        // then
        assertThat(actual).isNotNull();
    }
}
