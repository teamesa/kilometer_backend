package com.kilometer.backend.controller.dto.module.realtimearchive;

import static com.kilometer.common.Fixture.DIM_COLOR;
import static com.kilometer.common.Fixture.IS_LIKED;
import static com.kilometer.common.Fixture.LIKE_COUNT;
import static com.kilometer.common.Fixture.OPACITY;
import static com.kilometer.common.Fixture.REAL_TIME_ARCHIVE_DTO;
import static com.kilometer.common.Fixture.STAR_RATING;
import static com.kilometer.common.Fixture.USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("AdditionalInformationDto 는 ")
class MetaDataDtoTest {

    @DisplayName("AdditionalInformationDto를 생성해야 한다.")
    @Test
    void createAdditionalInformationDto() {
        MetaDataDto metaDataDto = MetaDataDto.from(REAL_TIME_ARCHIVE_DTO);

        assertAll(
                () -> assertThat(metaDataDto.getUser().getName()).isEqualTo(USER_NAME),
                () -> assertThat(metaDataDto.getHeart().isHeartClicked()).isEqualTo(IS_LIKED),
                () -> assertThat(metaDataDto.getLikeCount()).isEqualTo(LIKE_COUNT),
                () -> assertThat(metaDataDto.getStarRating()).isEqualTo(STAR_RATING),
                () -> assertThat(metaDataDto.getDimColor()).isEqualTo(DIM_COLOR),
                () -> assertThat(metaDataDto.getOpacity()).isEqualTo(OPACITY)
        );
    }
}
