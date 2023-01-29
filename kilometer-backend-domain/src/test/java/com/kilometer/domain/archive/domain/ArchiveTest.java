package com.kilometer.domain.archive.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kilometer.domain.archive.dto.PlaceInfo;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArchiveTest {

    @Test
    @DisplayName("Archive를 생성한다.")
    void createArchive() {
        // given & when
        Archive archive = Archive.create("comment", 1, true, List.of(), List.of());

        // then
        assertThat(archive).isNotNull();
    }

    @Test
    @DisplayName("Archive의 comment 가 null이면 예외가 발생한다.")
    void commentIsNotNull() {
        // given
        String invalidComment = null;

        // when & then
        assertThatThrownBy(() -> Archive.create(invalidComment, 1, true, List.of(), List.of()))
                .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("Archive의 starRating이 음수면 예외가 발생한다.")
    void archiveStarRatingIsNotNegative() {
        // given
        int invalidStarRating = -1;

        // when & then
        assertThatThrownBy(() -> Archive.create("김철수책상철책상", invalidStarRating, true, List.of(), List.of()))
                .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("Archive의 starRating이 5보다 크면 예외가 발생한다.")
    void archiveStarRatingIsPositiveOutOfRange() {
        // given
        int invalidStarRating = 6;

        // when & then
        assertThatThrownBy(() -> Archive.create("김철수책상철책상", invalidStarRating, true, List.of(), List.of()))
                .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("Archive의 ArchiveImageUrl이 null이면 예외가 발생한다.")
    void archiveImageUrlIsNotNull() {
        // given
        List<String> invalidImageUrls = null;

        // when & then
        assertThatThrownBy(() -> Archive.create("김철수책상철책상", 1, true, invalidImageUrls, List.of()))
                .isInstanceOf(ArchiveValidationException.class);
    }

    @Test
    @DisplayName("Archive의 UserVisitPlace가 null이면 예외가 발생한다.")
    void userVisitPlaceIsNotNull() {
        // given
        List<PlaceInfo> invalidPlaceInfos = null;

        // when & then
        assertThatThrownBy(() -> Archive.create("김철수책상철책상", 1, true, List.of(), invalidPlaceInfos))
                .isInstanceOf(ArchiveValidationException.class);
    }
}
