package com.kilometer.domain.archive.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.kilometer.domain.archive.domain.userVisitPlaces.UserVisitPlace;
import com.kilometer.domain.archive.exception.ArchiveValidationException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ArchiveTest {

    @Test
    @DisplayName("Archive를 생성한다.")
    void createArchive() {
        // given & when
        Archive archive = new Archive(1L, "comment", 1, true, List.of(), List.of());

        // then
        assertThat(archive).isNotNull();
    }

    @Test
    @DisplayName("Archive의 comment 가 null이면 예외가 발생한다.")
    void commentIsNotNull() {
        // given
        String invalidComment = null;

        // when & then
        assertThatThrownBy(() -> new Archive(1L, invalidComment, 1, true, List.of(), List.of()))
                .isInstanceOf(ArchiveValidationException.class)
                .hasMessage("입력된 comment가 없습니다.");
    }

    @Test
    @DisplayName("Archive의 starRating이 음수면 예외가 발생한다.")
    void archiveStarRatingIsNotNegative() {
        // given
        int invalidStarRating = -1;

        // when & then
        assertThatThrownBy(() -> new Archive(1L, "김철수책상철책상", invalidStarRating, true, List.of(), List.of()))
                .isInstanceOf(ArchiveValidationException.class)
                .hasMessage("별점은 0~5 사이의 양수이어야 합니다.");
    }

    @Test
    @DisplayName("Archive의 starRating이 5보다 크면 예외가 발생한다.")
    void archiveStarRatingIsPositiveOutOfRange() {
        // given
        int invalidStarRating = 6;

        // when & then
        assertThatThrownBy(() -> new Archive(1L, "김철수책상철책상", invalidStarRating, true, List.of(), List.of()))
                .isInstanceOf(ArchiveValidationException.class)
                .hasMessage("별점은 0~5 사이의 양수이어야 합니다.");
    }

    @Test
    @DisplayName("Archive의 ArchiveImages가 null이면 예외가 발생한다.")
    void archiveImageUrlIsNotNull() {
        // given
        List<String> invalidImageUrls = null;

        // when & then
        assertThatThrownBy(() -> new Archive(1L, "김철수책상철책상", 1, true, invalidImageUrls, List.of()))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("Archive의 UserVisitPlaces가 null이면 예외가 발생한다.")
    void userVisitPlaceIsNotNull() {
        // given
        List<UserVisitPlace> invalidUserVisitPlaces = null;

        // when & then
        assertThatThrownBy(() -> new Archive(1L, "김철수책상철책상", 1, true, List.of(), invalidUserVisitPlaces))
                .isInstanceOf(NullPointerException.class);
    }
}
