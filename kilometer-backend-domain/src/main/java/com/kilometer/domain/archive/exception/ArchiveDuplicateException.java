package com.kilometer.domain.archive.exception;

import com.kilometer.exception.KilometerErrorCode;
import com.kilometer.exception.KilometerException;

public class ArchiveDuplicateException extends KilometerException {

    public ArchiveDuplicateException() {
        super("");
    }

    public ArchiveDuplicateException(String message) {
        super(message);
    }

    @Override
    public KilometerErrorCode getErrorCode() {
        return KilometerErrorCode.ARCHIVE_NOT_FOUND;
    }
}
