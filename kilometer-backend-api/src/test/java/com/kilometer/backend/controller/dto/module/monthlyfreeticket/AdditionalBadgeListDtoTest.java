package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import static com.kilometer.common.Fixture.MONTHLY_FREE_TICKET_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AdditionalBadgeList 는 ")
class AdditionalBadgeListDtoTest {

    @DisplayName("AdditionalBadgeListDto 인스턴스를 생성한다.")
    @Test
    void from() {
        AdditionalBadgeListDto additionalBadgeListDto = AdditionalBadgeListDto.from(MONTHLY_FREE_TICKET_DTO);
        List<TypeBadgeDto> additionalBadges = additionalBadgeListDto.getAdditionalBadgeList();

        assertAll(
                () -> assertThat(additionalBadges.size()).isEqualTo(2),
                () -> assertThat(additionalBadges.get(0).getText()).isEqualTo("ON"),
                () -> assertThat(additionalBadges.get(1).getText()).isEqualTo("무료")
        );
    }
}
