package com.kilometer.domain.archive.exception;

import com.kilometer.exception.KilometerErrorCode;
import com.kilometer.exception.KilometerException;

public class ArchiveUnauthorizedException extends KilometerException {

    public ArchiveUnauthorizedException() {
        super("");
    }
    public ArchiveUnauthorizedException(String message) {
        super(message);
    }
    @Override
    public KilometerErrorCode getErrorCode() {
        return KilometerErrorCode.ARCHIVE_UNAUTHORIZED_EXCEPTION;
    }
}
