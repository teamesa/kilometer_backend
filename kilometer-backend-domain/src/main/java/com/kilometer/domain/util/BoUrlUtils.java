package com.kilometer.domain.util;

public class BoUrlUtils {

    private static final String ROOT = "/form";
    private static final String HOME_ROOT = "/home";

    public static final String ITEM_ROOT = ROOT + "/items";
    public static final String ITEM_ID = "/{itemId}";
    public static final String ITEM_ADD = "/add";
    public static final String ITEM_EDIT = "/edit" + ITEM_ID;
    public static final String ITEM_DELETE = "/delete" + ITEM_ID;


    public static final String IMAGE_ROOT = ROOT + "/image";
    public static final String IMAGE_DEFAULT = "/default";
    public static final String IMAGE_LIST = "/list";

    public static final String KEY_VISUAL = HOME_ROOT + "/key_visual";
    public static final String KEY_VISUAL_EDIT = KEY_VISUAL + "/edit";

    public static final String HOME_MODULES = HOME_ROOT + "/modules";
    public static final String HOME_MODULES_LIST = HOME_MODULES + "/list";
    public static final String HOME_MODULES_TYPE = HOME_MODULES + "/type";
    public static final String HOME_MODULES_EDIT = HOME_MODULES + "/edit";
    public static final String HOME_MODULES_DELETE = HOME_MODULES + "/delete";
}
