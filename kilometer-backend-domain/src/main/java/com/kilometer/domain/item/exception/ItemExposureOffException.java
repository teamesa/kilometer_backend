package com.kilometer.domain.item.exception;

import com.kilometer.exception.KilometerErrorCode;
import com.kilometer.exception.KilometerException;

public class ItemExposureOffException extends KilometerException {
    public ItemExposureOffException() {
        super("");
    }
    public ItemExposureOffException(String message) {
        super(message);
    }
    @Override
    public KilometerErrorCode getErrorCode() {
        return KilometerErrorCode.ITEM_EXPOSURE_OFF;
    }
}
