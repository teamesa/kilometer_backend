package com.kilometer.domain.util;

public class ApiUrlUtils {

    private static final String ROOT = "/api";
    public static final String SEARCH_ROOT = ROOT + "/search";
    public static final String SEARCH_AUTO_COMPLETE = "/auto-complete";
    public static final String PICK_ROOT = ROOT + "/pick";
    public static final String USER_ROOT = ROOT + "/user";
    public static final String PICK_ITEM = "/{itemId}";
    public static final String PICK_ITEM_PATTERN = PICK_ROOT + "/%s";

    public static final String ARCHIVE_ROOT = ROOT + "/archive";
    public static final String ARCHIVE_ID = "/{archiveId}";
    public static final String ARCHIVE_MY = "/my";
    public static final String ARCHIVE_DETAIL = "/detail" + ARCHIVE_ID;
    public static final String ARCHIVE_LIKE_ROOT = ARCHIVE_ROOT + "/like";
    public static final String ARCHIVE_LIKE_BY_ID = ARCHIVE_LIKE_ROOT + ARCHIVE_ID;
    public static final String LIKE_ITEM_PATTERN = ARCHIVE_LIKE_ROOT + "/%s";
    public static final String ARCHIVE_PATTERN = ARCHIVE_ROOT + "/%s";
    public static final String ARCHIVE_DETAIL_PATTERN = ARCHIVE_ROOT + "/detail" + "/%s";

    public static final String IMAGE_ROOT = ROOT + "/image";
    public static final String ITEM_ROOT = ROOT + "/item";
    public static final String ITEM_ID = "/{itemId}";
    public static final String ITEM_INFO = "/info" + ITEM_ID;
    public static final String ITEM_DETAIL = "/detail" + ITEM_ID;


    public static final String USER_ME = "/me";
    public static final String USER_PROFILE = "/profile";
    public static final String HOME_ROOT = ROOT + "/home";
    public static final String KEY_VISUAL = "key-visual";

    public static String getPickItemUrl(long itemId) {
        return String.format(PICK_ITEM_PATTERN + "?status=", itemId);
    }

    public static String getLikeArchiveUrl(long itemId) {
        return String.format(LIKE_ITEM_PATTERN + "?status=", itemId);
    }

    public static String getArchiveDetailUrl(long archiveId) {
        return String.format(ARCHIVE_DETAIL_PATTERN, archiveId);
    }

    public static String getArchiveUrl(Long archiveId) {
        return String.format(ARCHIVE_PATTERN, archiveId);
    }
}
