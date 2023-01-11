package com.kilometer.domain.archive.exception;

import com.kilometer.exception.KilometerErrorCode;
import com.kilometer.exception.KilometerException;

public class ArchiveNotFoundException extends KilometerException {

    public ArchiveNotFoundException() {
        super("");
    }

    public ArchiveNotFoundException(String message) {
        super(message);
    }

    @Override
    public KilometerErrorCode getErrorCode() {
        return KilometerErrorCode.ARCHIVE_NOT_FOUND;
    }
}
