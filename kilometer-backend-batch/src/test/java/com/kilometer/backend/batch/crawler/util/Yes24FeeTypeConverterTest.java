package com.kilometer.backend.batch.crawler.util;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("Yes24FeeTypeConverter 는 ")
class Yes24FeeTypeConverterTest {

    @DisplayName("가격이 들어왔을 때 유료여부를 판단해야 한다.")
    @ParameterizedTest(name = "{index} {displayName} fee={0} expected{1}")
    @CsvSource({"'', FREE", "0, FREE", "'1,000', COST", "1000, COST"})
    void convertFeeType(final String fee, final String expected) {
        assertThat(Yes24FeeTypeConverter.convertFeeType(fee)).isEqualTo(expected);
    }
}
