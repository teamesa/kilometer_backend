package com.kilometer.domain.user.exception;

import com.kilometer.exception.KilometerErrorCode;
import com.kilometer.exception.KilometerException;

public class UserNotFoundException extends KilometerException {

    public UserNotFoundException() {
        super("");
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public KilometerErrorCode getErrorCode() {
        return KilometerErrorCode.USER_NOT_FOUND;
    }
}
