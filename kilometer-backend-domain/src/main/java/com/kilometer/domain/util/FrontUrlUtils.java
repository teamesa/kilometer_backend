package com.kilometer.domain.util;

public class FrontUrlUtils {
    private static final String FRONT_DETAIL = "/detail/";
    private static final String FRONT_ARCHIVE = "/archive";
    private static final String FRONT_DETAIL_URL_PATTERN = FRONT_DETAIL + "%s";
    private static final String FRONT_MODIFY_TITLE = "아카이브 수정하기";
    private static final String FRONT_MODIFY_URL = FRONT_ARCHIVE + "/update";
    private static final String FRONT_CREATE_TITLE = "아카이브 기록하기";
    private static final String FRONT_CREATE_URL=FRONT_ARCHIVE;

    private static final String FRONT_MY_ARCHIVE_TITLE = "MY 아카이브";
    private static final String FRONT_MY_ARCHIVE_TITLE_PATTERN = FRONT_MY_ARCHIVE_TITLE+"(%d)";

    public static String getFrontDetailUrlPattern(long itemId) {
        return String.format(FRONT_DETAIL_URL_PATTERN, itemId);
    }

    public static String getFrontDetailPrefix() {
        return FRONT_DETAIL;
    }

    public static String getFrontMyArchiveTitlePattern(long count) {
        return String.format(FRONT_MY_ARCHIVE_TITLE_PATTERN,count);
    }

    public static String getFrontMyArchiveTitle() {
        return FRONT_MY_ARCHIVE_TITLE;
    }

    public static String getFrontModifyArchiveWithId(Long archiveId) {
        return String.format("%s?id=%d",FRONT_MODIFY_URL,archiveId);
    }

    public static String getFrontModifyTitle() { return FRONT_MODIFY_TITLE;}

    public static String getFrontCreateTitle() { return FRONT_CREATE_TITLE;}
    public static String getFrontCreateUrl() {
        return FRONT_CREATE_URL;
    }
}
