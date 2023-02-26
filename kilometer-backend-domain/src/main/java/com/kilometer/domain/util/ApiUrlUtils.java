package com.kilometer.domain.util;

public class ApiUrlUtils {

    private static final String ROOT = "/api";

    // Search
    public static final String SEARCH_ROOT = ROOT + "/search";
    public static final String SEARCH_AUTO_COMPLETE = SEARCH_ROOT+"/auto-complete";

    // Pick
    public static final String PICK_ROOT = ROOT + "/pick";
    public static final String PICK_MOST = PICK_ROOT + "/most";
    public static final String PICK_ITEM = PICK_ROOT + "/{itemId}";
    public static final String PICK_ITEM_PATTERN = PICK_ROOT + "/%s";

    // Archive
    public static final String ARCHIVE_ROOT = ROOT + "/archives";
    public static final String ARCHIVE_ID = ARCHIVE_ROOT + "/{archiveId}";
    public static final String ARCHIVE_MY = ARCHIVE_ROOT + "/my";
    public static final String ARCHIVE_PATTERN = ARCHIVE_ROOT + "/%s";

    // Archive Like
    public static final String LIKE_ROOT = "/like";
    public static final String ARCHIVE_LIKE = ARCHIVE_ID + LIKE_ROOT;
    public static final String ARCHIVE_LIKE_PATTERN = ARCHIVE_PATTERN + LIKE_ROOT;

    // Image
    public static final String IMAGE_ROOT = ROOT + "/image";

    // Item
    public static final String ITEM_ROOT = ROOT + "/items";
    public static final String ITEM_ID = ITEM_ROOT + "/{itemId}";
    public static final String ITEM_SUMMARY = ITEM_ID + "/summary";
    public static final String ITEM_ARCHIVES = ITEM_ID + "/archives";
    public static final String ITEM_PATTERN = ITEM_ROOT + "/%s";

    // User
    public static final String USER_ROOT = ROOT + "/users";
    public static final String USER_ME = USER_ROOT + "/me";
    public static final String USER_PROFILE = USER_ROOT + "/profile";

    // Home
    public static final String HOME_ROOT = ROOT + "/home";
    public static final String KEY_VISUAL = HOME_ROOT + "/key-visual";

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

    public static String getItemUrl(Long itemId) {
        return String.format(ITEM_PATTERN, itemId);
    }
}
