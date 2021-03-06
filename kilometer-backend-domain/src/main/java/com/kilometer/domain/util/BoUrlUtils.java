package com.kilometer.domain.util;

public class BoUrlUtils {

    private static final String ROOT = "/form";

    public static final String ITEM_ROOT = ROOT + "/items";
    public static final String ITEM_ID = "/{itemId}";
    public static final String ITEM_ADD = "/add";
    public static final String ITEM_EDIT = "/edit" + ITEM_ID;
    public static final String ITEM_DELETE = "/delete" + ITEM_ID;


    public static final String IMAGE_ROOT = ROOT + "/image";
    public static final String IMAGE_DEFAULT = "/default";
    public static final String IMAGE_LIST = "/list";
}
