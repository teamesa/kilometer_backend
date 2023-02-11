package com.kilometer.exception;

import lombok.Getter;

public enum KilometerErrorCode {

    ARCHIVE_NOT_FOUND("ALT-016", "Archive가 존재하지 않습니다."),
    ARCHIVE_VALIDATION_EXCEPTION("ALT-018", "요청이 잘못되었습니다.");

    @Getter
    private final String code;

    @Getter
    private final String message;

    KilometerErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
