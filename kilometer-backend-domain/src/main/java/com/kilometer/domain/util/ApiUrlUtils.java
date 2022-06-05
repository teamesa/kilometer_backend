package com.kilometer.domain.util;

public class ApiUrlUtils {
    private static final String ROOT = "/api";
    public static final String SEARCH_ROOT = ROOT + "/search";
    public static final String SEARCH_AUTO_COMPLETE = "/auto-complete";
    public static final String PICK_ROOT = ROOT + "/pick";
    public static final String PICK_ITEM = "/{itemId}";
    public static final String PICK_ITEM_PATTERN = PICK_ROOT + "/%s";

    public static String getPickItemUrl(long itemId) {
        return String.format(PICK_ITEM_PATTERN + "?status=", itemId);
    }
}
