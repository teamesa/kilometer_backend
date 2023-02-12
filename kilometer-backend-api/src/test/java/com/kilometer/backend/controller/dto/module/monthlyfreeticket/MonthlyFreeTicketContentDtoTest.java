package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import static com.kilometer.common.Fixture.ITEM_ID;
import static com.kilometer.common.Fixture.MONTHLY_FREE_TICKET_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ContentDto 는 ")
class MonthlyFreeTicketContentDtoTest {

    @DisplayName("ContentDto 인스턴스를 생성한다.")
    @Test
    void from() {
        MonthlyFreeTicketContentDto monthlyFreeTicketContentDto = MonthlyFreeTicketContentDto.from(MONTHLY_FREE_TICKET_DTO);

        assertAll(
                () -> assertThat(monthlyFreeTicketContentDto.getId()).isEqualTo(ITEM_ID),
                () -> assertThat(monthlyFreeTicketContentDto.getAdditionalBadgeList().size()).isEqualTo(2)
        );
    }
}
