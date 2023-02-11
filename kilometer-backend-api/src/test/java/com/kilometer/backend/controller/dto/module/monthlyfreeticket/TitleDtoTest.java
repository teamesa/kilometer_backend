package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import static com.kilometer.common.Fixture.ITEM_DETAIL_URL;
import static com.kilometer.common.Fixture.MONTHLY_FREE_TICKET_DTO;
import static com.kilometer.common.Fixture.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TitleDto 는 ")
class TitleDtoTest {

    @DisplayName("TitleDto 인스턴스를 생성한다.")
    @Test
    void from() {
        TitleDto titleDto = TitleDto.from(MONTHLY_FREE_TICKET_DTO);

        assertAll(
                () -> assertThat(titleDto.getText()).isEqualTo(TITLE),
                () -> assertThat(titleDto.getLink()).isEqualTo(ITEM_DETAIL_URL)
        );
    }
}
