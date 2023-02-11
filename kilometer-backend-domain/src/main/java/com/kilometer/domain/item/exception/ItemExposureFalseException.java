package com.kilometer.domain.item.exception;

import com.kilometer.exception.KilometerErrorCode;
import com.kilometer.exception.KilometerException;

public class ItemExposureFalseException extends KilometerException {
    public ItemExposureFalseException() {
        super("");
    }
    public ItemExposureFalseException(String message) {
        super(message);
    }
    @Override
    public KilometerErrorCode getErrorCode() {
        return KilometerErrorCode.ITEM_EXPOSURE_FALSE;
    }
}
