package com.kilometer.domain.archive.exception;

import com.kilometer.exception.KilometerErrorCode;
import com.kilometer.exception.KilometerException;

public class ArchiveValidationException extends KilometerException {

    public ArchiveValidationException() {
        super("");
    }

    public ArchiveValidationException(String message) {
        super(message);
    }

    @Override
    public KilometerErrorCode getErrorCode() {
        return null;
    }
}
