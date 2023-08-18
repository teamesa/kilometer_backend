package com.kilometer.domain.item.enumType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FeeType {

    FREE("무료"), COST("유료");

    private static final String PRICE_REGEX = "^$|(?<!\\d)(?<!\\d\\.)(?:\\d{1,3}(?:,\\d{3})*|\\d+)(?:\\.\\d{1,2})?(?!\\.?\\d)";

    private final String description;

    public static FeeType findFeeType(final String fee) {
        validateFee(fee);
        if (fee.isBlank() || fee.equals("0")) {
            return FREE;
        }
        return COST;
    }

    private static void validateFee(final String fee) {
        if (!fee.matches(PRICE_REGEX)) {
            throw new IllegalArgumentException("가격은 정수여야 합니다.");
        }
    }
}
