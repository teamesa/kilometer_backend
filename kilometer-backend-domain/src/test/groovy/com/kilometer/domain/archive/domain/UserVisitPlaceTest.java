package com.kilometer.domain.archive.domain;

import static com.kilometer.common.statics.Statics.장소_종류;
import static com.kilometer.common.statics.Statics.카페_도로명_주소;
import static com.kilometer.common.statics.Statics.카페_이름;
import static com.kilometer.common.statics.Statics.카페_지번_주소;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kilometer.domain.archive.domain.userVisitPlace.UserVisitPlace;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserVisitPlaceTest {

    @Test
    @DisplayName("UserVisitPlace를 생성한다.")
    void createUserVisitPlace() {
        // given & when
        UserVisitPlace actual = UserVisitPlace.createUserVisitPlace(장소_종류, 카페_이름, 카페_지번_주소, 카페_도로명_주소);

        // then
        assertThat(actual).isInstanceOf(UserVisitPlace.class);
    }

    // TODO: PlaceType에 없는경우에 커스텀 예외가 발생해야함 (프론트엔드와 PlaceInfo 요청 값에 대한 논의 필요)
//    @Test
//    @DisplayName("UserVisitPlace를 생성 할 때, 없는 장소 종류이면 예외가 발생한다.")
//    void createUserVisitPlace_emptyPlace() {
//        // given
//        String invalidPlaceType = "없는 장소";
//
//        // when & then
//        assertThatThrownBy(() -> UserVisitPlace.createUserVisitPlace(invalidPlaceType, 카페_이름, 카페_지번_주소, 카페_도로명_주소))
//            .isInstanceOf(ArchiveValidationException.class)
//            .hasMessage("일치하는 방문 장소 종류가 없습니다.");
//    }

    @Test
    @DisplayName("UserVisitPlace를 생성 할 때, 카페이름이 공백이면 예외가 발생한다.")
    void createUserVisitPlace_emptyName() {
        // given
        String invalidCafeName = "   ";

        // when & then
        assertThatThrownBy(() -> UserVisitPlace.createUserVisitPlace(장소_종류, invalidCafeName, 카페_지번_주소, 카페_도로명_주소))
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage("입력된 장소명이 없습니다.");
    }

    @Test
    @DisplayName("UserVisitPlace를 생성 할 때, 지번 주소가 공백이면 예외가 발생한다.")
    void createUserVisitPlace_emptyAddress() {
        // given
        String invalidCafeAddress = "   ";

        // when & then
        assertThatThrownBy(() -> UserVisitPlace.createUserVisitPlace(장소_종류, 카페_이름, invalidCafeAddress, 카페_도로명_주소))
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage("입력된 지번 주소가 없습니다.");
    }

    @Test
    @DisplayName("UserVisitPlace를 생성 할 때, 도로명 주소가 공백이면 예외가 발생한다.")
    void createUserVisitPlace_emptyRoadAddress() {
        // given
        String invalidRoadAddress = "   ";

        // when & then
        assertThatThrownBy(() -> UserVisitPlace.createUserVisitPlace(장소_종류, 카페_이름, 카페_지번_주소, invalidRoadAddress))
            .isInstanceOf(ArchiveValidationException.class)
            .hasMessage("입력된 도로명 주소가 없습니다.");
    }
}
