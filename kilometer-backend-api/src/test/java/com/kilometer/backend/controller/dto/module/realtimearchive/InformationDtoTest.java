package com.kilometer.backend.controller.dto.module.realtimearchive;

import static com.kilometer.common.Fixture.COMMENT;
import static com.kilometer.common.Fixture.PLACE_NAME;
import static com.kilometer.common.Fixture.REAL_TIME_ARCHIVE_DTO;
import static com.kilometer.common.Fixture.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("InformationDto 는 ")
class InformationDtoTest {

    @DisplayName("InformationDto 를 생성한다.")
    @Test
    void createInformationDto() {
        InformationDto informationDto = InformationDto.from(REAL_TIME_ARCHIVE_DTO);

        assertAll(
                () -> assertThat(informationDto.getPlaces()).isEqualTo(PLACE_NAME),
                () -> assertThat(informationDto.getComment()).isEqualTo(COMMENT),
                () -> assertThat(informationDto.getTitle().getValue()).isEqualTo(TITLE)
        );
    }
}
