package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import static com.kilometer.common.Fixture.ARCHIVE_COUNT;
import static com.kilometer.common.Fixture.GRADE;
import static com.kilometer.common.Fixture.MONTHLY_FREE_TICKET_DTO;
import static com.kilometer.common.Fixture.PICK_COUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ListItemAdditionalInfo 는 ")
class ListItemAdditionalInfoDtoTest {

    @DisplayName("ListItemAdditionalInfor 인스턴스를 생성한다.")
    @Test
    void from() {
        ListItemAdditionalInfoDto listItemAdditionalInfoDto = ListItemAdditionalInfoDto.from(MONTHLY_FREE_TICKET_DTO);

        assertAll(
                () -> assertThat(listItemAdditionalInfoDto.getHeartCount()).isEqualTo(PICK_COUNT),
                () -> assertThat(listItemAdditionalInfoDto.getGrade()).isEqualTo(GRADE),
                () -> assertThat(listItemAdditionalInfoDto.getArchiveCount()).isEqualTo(ARCHIVE_COUNT)
        );
    }
}
