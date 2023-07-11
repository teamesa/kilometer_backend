package com.kilometer.backend.batch.crawler.util;

import com.kilometer.domain.item.enumType.FeeType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Yes24FeeTypeConverter {

    public static String convertFeeType(final String fee) {
        if (fee.isBlank() || fee.equals("0")) {
            return FeeType.FREE.name();
        }
        return FeeType.COST.name();
    }
}
