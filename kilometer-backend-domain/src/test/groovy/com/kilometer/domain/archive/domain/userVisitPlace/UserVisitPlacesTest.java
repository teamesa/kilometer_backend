package com.kilometer.domain.archive.domain.userVisitPlace;

import static com.kilometer.common.statics.Statics.사용자_방문장소들;
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
        List<UserVisitPlace> userVisitPlaces = 사용자_방문장소들;

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

        // when
        UserVisitPlaces actual = new UserVisitPlaces(invalidUserVisitPlaces);

        // when & then
        assertThatThrownBy(actual::validate)
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage("입력된 방문 장소가 없습니다.");
    }
}
