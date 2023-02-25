package com.kilometer.backend.controller.dto.module.realtimearchive;

import static com.kilometer.common.Fixture.IS_LIKED;
import static com.kilometer.common.Fixture.REAL_TIME_ARCHIVE_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HeartDto 는 ")
class HeartDtoTest {


    @DisplayName("HeartDto 를 생성해야 한다.")
    @Test
    void createHeartDto() {
        HeartDto heartDto = HeartDto.from(REAL_TIME_ARCHIVE_DTO);

        assertAll(
                () -> assertThat(heartDto.isHeartClicked()).isEqualTo(IS_LIKED),
                () -> assertThat(heartDto.getLink()).isEqualTo("/api/archives/1/like?status=")
        );
    }
}
