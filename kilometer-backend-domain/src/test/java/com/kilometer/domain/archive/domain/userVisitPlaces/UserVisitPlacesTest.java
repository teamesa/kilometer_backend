package com.kilometer.domain.archive.domain.userVisitPlaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
import com.kilometer.domain.archive.userVisitPlace.UserVisitPlaceEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserVisitPlacesTest {

    @Test
    @DisplayName("UserVisitPlaces를 생성한다.")
    void createUserVisitPlaces() {
        // given
        List<UserVisitPlace> userVisitPlaces = List.of();

        // when
        UserVisitPlaces actual = new UserVisitPlaces(userVisitPlaces);

        // then
        assertThat(actual).isNotNull();
    }

    @Test
    @DisplayName("UserVisitPlaces를 생성 할 때, 필드가 null이면 예외가 발생한다.")
    void createUserVisitPlaces_nullException() {
        // given
        List<UserVisitPlace> invalidUserVisitPlaces = null;

        // when & then
        assertThatThrownBy(() -> new UserVisitPlaces(invalidUserVisitPlaces))
                .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("UserVisitPlaces를 Entity 객체로 변환한다.")
    void convertToEntity() {
        // given
        UserVisitPlace userVisitPlace = new UserVisitPlace(1L, "CAFE", "cafe", "address", "roadAddress");
        UserVisitPlaces userVisitPlaces = new UserVisitPlaces(List.of(userVisitPlace));

        // when
        List<UserVisitPlaceEntity> actual = userVisitPlaces.toEntity();

        // then
        assertAll(
                () -> assertThat(actual).hasSize(1)
        );
    }
}
