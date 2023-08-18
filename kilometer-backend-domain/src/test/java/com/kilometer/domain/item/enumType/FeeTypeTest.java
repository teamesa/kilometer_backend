package com.kilometer.domain.item.enumType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("FeeType 은 ")
class FeeTypeTest {

    @DisplayName("올바른 가격 종류를 찾을 때 ")
    @Nested
    class FindFeeTypeTest {

        @DisplayName("올바른 가격 형식이 들어오면 그에 맞는 가격 종류를 반환한다.")
        @ParameterizedTest(name = "{index} {displayName} fee={0}, expected={1}")
        @CsvSource({"1000, COST", "'1,000', COST", "'', FREE", "0, FREE"})
        void findFeeTypeSuccess(final String fee, final FeeType expected) {
            FeeType actual = FeeType.findFeeType(fee);
            assertThat(actual).isEqualTo(expected);
        }

        @DisplayName("가격 표시에 사용되지 않는 문자가 포함되면 에러를 반환한다.")
        @ParameterizedTest(name = "{index} {displayName} fee={0}")
        @ValueSource(strings = {"-1", "1,a00", "-2000"})
        void wrongFeeFormat(final String fee) {
           assertThatThrownBy(() -> FeeType.findFeeType(fee))
                   .isInstanceOf(IllegalArgumentException.class)
                   .hasMessage("가격은 정수여야 합니다.");
        }
    }
}
