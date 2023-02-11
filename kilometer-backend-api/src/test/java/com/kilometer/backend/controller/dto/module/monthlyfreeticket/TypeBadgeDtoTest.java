package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import static com.kilometer.common.Fixture.EXHIBITION_TYPE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TypeBadgeDto 는 ")
class TypeBadgeDtoTest {

    @DisplayName("TypeBadgeDto 인스턴스를 생성한다.")
    @Test
    void from() {
        TypeBadgeDto typeBadgeDto = TypeBadgeDto.of(EXHIBITION_TYPE.getDescription(), true);

        assertAll(
                () -> assertThat(typeBadgeDto.getText()).isEqualTo(EXHIBITION_TYPE.getDescription()),
                () -> assertThat(typeBadgeDto.isTypeBadge()).isTrue()
        );
    }
}
