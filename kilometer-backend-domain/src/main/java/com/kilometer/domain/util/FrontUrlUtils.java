package com.kilometer.domain.util;

public class FrontUrlUtils {
    private static final String FRONT_DETAIL = "/detail/";
    private static final String FRONT_ARCHIVE = "/archive";
    private static final String FRONT_DETAIL_URL_PATTERN = FRONT_DETAIL + "%s";

    public static String getFrontDetailUrlPattern(long itemId) {
        return String.format(FRONT_DETAIL_URL_PATTERN, itemId);
    }

    public static String getFrontDetailPrefix() {
        return FRONT_DETAIL;
    }

    public static String getFrontArchivePrefix() { return FRONT_ARCHIVE;}
}
