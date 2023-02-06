package com.kilometer.backend.util;

public class StringConvertor {

    public static String convertNullToBlank(String target) {
        if (target == null) {
            return "";
        }
        return target;
    }
}
