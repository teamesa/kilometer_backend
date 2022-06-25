package com.kilometer.domain.util;

public class ApiUrlUtils {
    private static final String ROOT = "/api";
    public static final String SEARCH_ROOT = ROOT + "/search";
    public static final String SEARCH_AUTO_COMPLETE = "/auto-complete";
    public static final String PICK_ROOT = ROOT + "/pick";
    public static final String PICK_ITEM = "/{itemId}";
    public static final String PICK_ITEM_PATTERN = PICK_ROOT + "/%s";

    public static final String ARCHIVE_ROOT = ROOT + "/archive";

    public static final String ARCHIVE_ITEM = "/{itemId}";


    public static final String ARCHIVE_MY = "/my";

    public static final String IMAGE_ROOT = ROOT + "/image";

    public static final String LIKE_ROOT = ROOT + "/like";
    public static final String LIKE_ITEM_PATTERN = LIKE_ROOT + "/%s";

    public static String getPickItemUrl(long itemId) {
        return String.format(PICK_ITEM_PATTERN + "?status=", itemId);
    }

    public static String getLikeArchiveUrl(long itemId) {
        return String.format(LIKE_ITEM_PATTERN+"?status=",itemId);
    }
}
