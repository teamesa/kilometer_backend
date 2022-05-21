package com.kilometer.domain.util;

public class FrontUrlUtils {
    private static final String FRONT_DETAIL_URL_PATTERN = "/details/%s";

    public static String getFrontDetailUrlPattern(long itemId) {
        return String.format(FRONT_DETAIL_URL_PATTERN, itemId);
    }
}
