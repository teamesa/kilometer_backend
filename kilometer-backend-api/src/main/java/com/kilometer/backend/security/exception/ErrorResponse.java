package com.kilometer.backend.security.exception;

import com.kilometer.exception.KilometerErrorCode;

@SuppressWarnings("unused")
public class ErrorResponse {
    private final String code;
    private final String message;

    private ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorResponse from(KilometerErrorCode errorCode) {
        return new ErrorResponse(errorCode.getCode(), errorCode.getMessage());
    }
}
