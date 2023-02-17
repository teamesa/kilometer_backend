package com.kilometer.backend.controller.dto.module.realtimearchive;

import static com.kilometer.common.Fixture.BOTTOM_TITLE;
import static com.kilometer.common.Fixture.REAL_TIME_ARCHIVE_RESPONSE_MODULE_RESPONSE_DTO;
import static com.kilometer.common.Fixture.TOP_TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ArchivesResponse 는 ")
class ArchivesResponseTest {

    @DisplayName("ArchiveResponse를 생성해야 한다.")
    @Test
    void createArchiveResponse() {
        ArchivesResponse archiveResponse = ArchivesResponse.from(REAL_TIME_ARCHIVE_RESPONSE_MODULE_RESPONSE_DTO);

        assertAll(
                () -> assertThat(archiveResponse.getTopTitle()).isEqualTo(TOP_TITLE),
                () -> assertThat(archiveResponse.getBottomTitle()).isEqualTo(BOTTOM_TITLE),
                () -> assertThat(archiveResponse.getArchives().size()).isEqualTo(1)
        );
    }
}
