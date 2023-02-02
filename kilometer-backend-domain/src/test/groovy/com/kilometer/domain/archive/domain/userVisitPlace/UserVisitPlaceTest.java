package com.kilometer.domain.archive.domain.userVisitPlace;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserVisitPlaceTest {

    @Test
    @DisplayName("UserVisitPlace를 생성한다.")
    void createUserVisitPlace() {
        // given & when
        UserVisitPlace actual = new UserVisitPlace(1L, "CAFE", "cafe", "address", "roadAddress");

        // then
        assertThat(actual).isNotNull();
    }
}
