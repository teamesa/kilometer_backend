package com.kilometer.domain.util;

public class ApiUrlUtils {

    private static final String ROOT = "/api";
    public static final String SEARCH_ROOT = ROOT + "/search";
    public static final String SEARCH_AUTO_COMPLETE = "/auto-complete";
    public static final String PICK_ROOT = ROOT + "/pick";
    public static final String PICK_MOST = "/most";
    public static final String USER_ROOT = ROOT + "/user";
    public static final String PICK_ITEM = "/{itemId}";
    public static final String PICK_ITEM_PATTERN = PICK_ROOT + "/%s";

    public static final String ARCHIVE_ROOT = ROOT + "/archives";
    public static final String ARCHIVE_ID = "/{archiveId}";
    public static final String ARCHIVE_MY = "/my";
    public static final String LIKE_ROOT = "/like";
    public static final String ARCHIVE_LIKE = ARCHIVE_ID + LIKE_ROOT;
    public static final String ARCHIVE_PATTERN = ARCHIVE_ROOT + "/%s";
    public static final String ARCHIVE_LIKE_PATTERN = ARCHIVE_PATTERN + LIKE_ROOT;

    public static final String IMAGE_ROOT = ROOT + "/image";
    public static final String ITEM_ROOT = ROOT + "/item";
    public static final String ITEM_ID = "/{itemId}";
    public static final String ITEM_INFO = "/info" + ITEM_ID;
    public static final String ITEM_DETAIL = "/detail" + ITEM_ID;


    public static final String USER_ME = "/me";
    public static final String USER_PROFILE = "/profile";
    public static final String HOME_ROOT = ROOT + "/home";
    public static final String KEY_VISUAL = "key-visual";

    public static final String AUTHENTICATION_ROOT = ROOT + "/authentication";

    public static String getPickItemUrl(long itemId) {
        return String.format(PICK_ITEM_PATTERN + "?status=", itemId);
    }

    public static String getLikeArchiveUrl(long itemId) {
        return String.format(ARCHIVE_LIKE_PATTERN + "?status=", itemId);
    }

    public static String getArchiveUrl(Long archiveId) {
        return String.format(ARCHIVE_PATTERN, archiveId);
    }
}
