package com.kilometer.exception;

public abstract class KilometerException extends RuntimeException{
    private final KilometerErrorCode errorCode;
    private final String message;

    public KilometerException(String message) {
        this.errorCode = getErrorCode();
        this.message = (message==null||message.isBlank()) ? errorCode.getMessage() : message;
    }

    public abstract KilometerErrorCode getErrorCode();

}
