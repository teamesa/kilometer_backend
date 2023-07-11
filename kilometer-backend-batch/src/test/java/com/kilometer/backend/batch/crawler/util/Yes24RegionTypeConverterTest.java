package com.kilometer.backend.batch.crawler.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Yes24RegionTypeConverter 는 ")
class Yes24RegionTypeConverterTest {

    @DisplayName("지역이름을 RegionType으로 변경할 때 ")
    @Nested
    class RegionTypeConvertTest {

        @DisplayName("존재하는 지역 이름이면 RegionType을 반환한다.")
        @ParameterizedTest(name = "{index} {displayName} region={0} expected={1}")
        @CsvSource({"서울, SEOUL", "경기도, GYEONGGI", "제주, JEJU"})
        void hasRegionType(final String region, final String expected) {
            assertThat(Yes24RegionTypeConverter.of(region)).isEqualTo(expected);
        }

        @DisplayName("존재하지 않는 지역 이름이면 예외를 던진다.")
        @Test
        void wrongRegionType() {
            assertThatThrownBy(() -> Yes24RegionTypeConverter.of("wrongRegion"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("존재하지 않는 지역입니다.");
        }
    }
}
