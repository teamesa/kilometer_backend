package com.kilometer.domain.archive.domain.userVisitPlaces;

import static org.assertj.core.api.Assertions.assertThat;

import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
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

    @Test
    @DisplayName("UserVisitPlace를 Entity 객체로 변환한다.")
    void convertToEntity() {
        // given
        UserVisitPlace userVisitPlace = new UserVisitPlace(1L, "CAFE", "cafe", "address", "roadAddress");

        // when
        UserVisitPlaceEntity actual = userVisitPlace.toEntity();

        // then
        assertThat(actual.getAddress()).isEqualTo("address");
    }
}
