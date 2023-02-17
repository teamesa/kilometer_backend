package com.kilometer.backend.controller.dto.module.monthlyfreeticket;

import static com.kilometer.common.Fixture.ITEM_DETAIL_URL;
import static com.kilometer.common.Fixture.MONTHLY_FREE_TICKET_DTO;
import static com.kilometer.common.Fixture.THUMBNAIL_IMAGE_URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PresentationImageDto 는 ")
class PresentationImageDtoTest {

    @DisplayName("PresentationImageDto 인스턴스를 생성한다.")
    @Test
    void from() {
        PresentationImageDto presentationImageDto = PresentationImageDto.from(MONTHLY_FREE_TICKET_DTO);

        assertAll(
                () -> assertThat(presentationImageDto.getUrl()).isEqualTo(THUMBNAIL_IMAGE_URL),
                () -> assertThat(presentationImageDto.getLink()).isEqualTo(ITEM_DETAIL_URL),
                () -> assertThat(presentationImageDto.getDimColor()).isEqualTo(null),
                () -> assertThat(presentationImageDto.getOpacity()).isEqualTo("0"),
                () -> assertThat(presentationImageDto.isDimTarget()).isFalse()
        );
    }
}
