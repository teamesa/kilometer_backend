package com.kilometer.backend.batch.crawler.util;

import com.kilometer.domain.item.enumType.FeeType;

public class Yes24FeeTypeConverter {

    public static String convertFeeType(final String fee) {
        if (fee.isBlank()) {
            return FeeType.FREE.name();
        }
        return FeeType.COST.name();
    }
}
