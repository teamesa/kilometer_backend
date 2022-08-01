package com.kilometer.domain.util;

public class FrontUrlUtils {

    private static final String FRONT_DETAIL = "/detail/";
    private static final String FRONT_ARCHIVE = "/archive";
    private static final String FRONT_DETAIL_URL_PATTERN = FRONT_DETAIL + "%s";
    private static final String FRONT_MY_ARCHIVE_TITLE = "MY 아카이브";
    private static final String FRONT_MY_ARCHIVE_TITLE_PATTERN = FRONT_MY_ARCHIVE_TITLE + "(%d)";
    private static final String FRONT_ARCHIVE_MODIFY = FRONT_ARCHIVE + "/update";

    public static String getFrontDetailUrlPattern(long itemId) {
        return String.format(FRONT_DETAIL_URL_PATTERN, itemId);
    }

    public static String getFrontDetailPrefix() {
        return FRONT_DETAIL;
    }

    public static String getFrontMyArchiveTitlePattern(long count) {
        return String.format(FRONT_MY_ARCHIVE_TITLE_PATTERN, count);
    }

    public static String getFrontMyArchiveTitle() {
        return FRONT_MY_ARCHIVE_TITLE;
    }

    public static String getFrontArchivePrefix() {
        return FRONT_ARCHIVE;
    }

    public static String getFrontArchiveModify() {
        return FRONT_ARCHIVE_MODIFY;
    }
}
