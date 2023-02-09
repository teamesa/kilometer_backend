package com.kilometer.backend.controller.dto.module.realtimearchive;

import static com.kilometer.common.Fixture.ITEM_DETAIL_URL;
import static com.kilometer.common.Fixture.REAL_TIME_ARCHIVE_DTO;
import static com.kilometer.common.Fixture.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TitleDto 는 ")
class TitleDtoTest {

    @DisplayName("TitleDto 를 생성해야 한다.")
    @Test
    void createTitleDto() {
        TitleDto titleDto = TitleDto.from(REAL_TIME_ARCHIVE_DTO);

        assertAll(
                () -> assertThat(titleDto.getLink()).isEqualTo(ITEM_DETAIL_URL),
                () -> assertThat(titleDto.getValue()).isEqualTo(TITLE)
        );
    }
}
