package com.kilometer.backend.batch.crawler.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Yes24ExhibitionTypeConverter 는 ")
class ExhibitionTypeConverterTest {

    @DisplayName("Yes24 공연 표기방식을 ExhibitionType으로 변환한다.")
    @ParameterizedTest(name = "{index} {displayName} exhibition={0} expected{1}")
    @CsvSource({"콘서트, CONCERT", "전시/행사, EXHIBITION", "버스킹, ALL"})
    void convertYes24ExhibitionTypeToExhibitionType(final String exhibition, final String expected) {
        assertThat(ExhibitionTypeConverter.of(exhibition)).isEqualTo(expected);
    }
}
