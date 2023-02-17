package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import static com.kilometer.common.Fixture.ITEM_ID;
import static com.kilometer.common.Fixture.MONTHLY_FREE_TICKET_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kilometer.domain.util.ApiUrlUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("HeartDto 는 ")
class HeartDtoTest {

    @DisplayName("HeartDto 인스턴스를 생성할 수 있어야 한다.")
    @Test
    void from() {
        HeartDto heartDto = HeartDto.from(MONTHLY_FREE_TICKET_DTO);

        assertAll(
                () -> assertThat(heartDto.isHeartClicked()).isTrue(),
                () -> assertThat(heartDto.getLink()).isEqualTo(ApiUrlUtils.getPickItemUrl(ITEM_ID)),
                () -> assertThat(heartDto.getId()).isEqualTo(1)
        );
    }
}
