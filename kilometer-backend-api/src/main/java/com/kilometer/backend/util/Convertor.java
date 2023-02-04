package com.kilometer.backend.util;

public class Convertor {

    public static String convertNullToBlank(String target) {
        if (target == null) {
            return "";
        }
        return target;
    }
}
