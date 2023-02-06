package com.kilometer.backend.controller.dto.module.realtimearchive;

import static com.kilometer.common.Fixture.IMAGE_URL;
import static com.kilometer.common.Fixture.ITEM_DETAIL_URL;
import static com.kilometer.common.Fixture.REAL_TIME_ARCHIVE_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PhotoDto 는 ")
class PhotoDtoTest {

    @DisplayName("PhotoDto를 생성해야 한다.")
    @Test
    void createPhotoDto() {
        PhotoDto photoDto = PhotoDto.from(REAL_TIME_ARCHIVE_DTO);

        assertAll(
                () -> assertThat(photoDto.getPhotoUrl()).isEqualTo(IMAGE_URL),
                () -> assertThat(photoDto.getLink()).isEqualTo(ITEM_DETAIL_URL)
        );
    }
}
