package com.kilometer.domain.item.enumType;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("ExposureType 은 ")
class ExposureTypeTest {

    @DisplayName("날짜가 주어졌을 때, 전시 상황을 알려주어야 한다.")
    @ParameterizedTest(name = "{index} {displayName} startDate={0} endDate={1}")
    @MethodSource(value = "localDateProvider")
    void findExposureType(final LocalDate startDate, final LocalDate endDate, final ExposureType expected) {
        assertThat(ExposureType.findExposureType(startDate, endDate)).isEqualTo(expected);
    }

    static Stream<Arguments> localDateProvider() {
        LocalDate now = LocalDate.now();
        LocalDate yesterday = now.minusDays(1);
        LocalDate tomorrow = now.plusDays(1);
        return Stream.of(
                Arguments.arguments(yesterday, tomorrow, ExposureType.ON),
                Arguments.arguments(now, tomorrow, ExposureType.ON),
                Arguments.arguments(yesterday, now, ExposureType.ON),
                Arguments.arguments(tomorrow, tomorrow.plusDays(1), ExposureType.OFF),
                Arguments.arguments(yesterday.minusDays(1), yesterday, ExposureType.OFF)
        );
    }
}
