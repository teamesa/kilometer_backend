package com.kilometer.domain.archive.domain.userVisitPlace;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kilometer.domain.archive.exception.ArchiveValidationException;
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
}
