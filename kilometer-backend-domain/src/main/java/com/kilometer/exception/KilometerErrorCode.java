package com.kilometer.exception;

import lombok.Getter;

public enum KilometerErrorCode {

    ARCHIVE_NOT_FOUND("ALT-016", "Archive가 존재하지 않습니다."),
    ITEM_EXPOSURE_OFF("ALT-017", "해당 컨텐츠는 관리자에 의해 삭제되었습니다."),
    ARCHIVE_VALIDATION_EXCEPTION("ALT-018", "요청이 잘못되었습니다."),
    ARCHIVE_UNAUTHORIZED_EXCEPTION("ALT-019", "권한이 존재하지 않습니다."),
    ITEM_NOT_FOUND("ALT-020", "존재 하지 않는 컨텐츠입니다.");

    @Getter
    private final String code;

    @Getter
    private final String message;

    KilometerErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
