package com.kilometer.backend.controller.dto.module.realtimearchive;

import static com.kilometer.common.Fixture.COMMENT;
import static com.kilometer.common.Fixture.IMAGE_URL;
import static com.kilometer.common.Fixture.LIKE_COUNT;
import static com.kilometer.common.Fixture.REAL_TIME_ARCHIVE_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ArchiveDto 는 ")
class ArchiveDtoTest {

    @DisplayName("ArchiveDto 를 생성해야 한다.")
    @Test
    void createArchiveDto() {
        ArchiveDto archiveDto = ArchiveDto.from(REAL_TIME_ARCHIVE_DTO);

        assertAll(
                () -> assertThat(archiveDto.getPhoto().getPhotoUrl()).isEqualTo(IMAGE_URL),
                () -> assertThat(archiveDto.getAdditionalInformation().getLikeCount()).isEqualTo(LIKE_COUNT),
                () -> assertThat(archiveDto.getInformation().getComment()).isEqualTo(COMMENT)
        );
    }
}
