package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import static com.kilometer.common.Fixture.BOTTOM_TITLE;
import static com.kilometer.common.Fixture.MONTHLY_FREE_TICKET_RESPONSE_MODULE_RESPONSE_DTO;
import static com.kilometer.common.Fixture.TOP_TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("MonthlyFreeTicketResponse 는 ")
class MonthlyFreeTicketsResponseTest {

    @DisplayName("MonthlyFreeTicektResponse 인스턴스를 생성한다..")
    @Test
    void from() {
        MonthlyFreeTicketsResponse monthlyFreeTicketsResponse = MonthlyFreeTicketsResponse.from(
                MONTHLY_FREE_TICKET_RESPONSE_MODULE_RESPONSE_DTO);

        assertAll(
                () -> assertThat(monthlyFreeTicketsResponse.getTopTitle()).isEqualTo(TOP_TITLE),
                () -> assertThat(monthlyFreeTicketsResponse.getBottomTitle()).isEqualTo(BOTTOM_TITLE),
                () -> assertThat(monthlyFreeTicketsResponse.getContents().size()).isEqualTo(1)
        );
    }
}
