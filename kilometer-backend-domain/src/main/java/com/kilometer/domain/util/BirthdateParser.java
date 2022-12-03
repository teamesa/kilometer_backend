package com.kilometer.domain.util;

import java.time.LocalDateTime;

public class BirthdateParser {

    public static LocalDateTime generateBirthDate(String birthYear, String birthDay) {
        try {
            return LocalDateTime.parse(String.format("%s-%s", birthYear, birthDay));
        } catch (Exception e) {
            return null;
        }
    }
}
