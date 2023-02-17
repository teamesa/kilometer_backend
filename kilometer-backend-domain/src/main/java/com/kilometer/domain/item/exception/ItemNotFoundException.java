package com.kilometer.domain.item.exception;

import com.kilometer.exception.KilometerErrorCode;
import com.kilometer.exception.KilometerException;

public class ItemNotFoundException extends KilometerException {
    public ItemNotFoundException() {
        super("");
    }

    public ItemNotFoundException(String message) {
        super(message);
    }

    @Override
    public KilometerErrorCode getErrorCode() {
        return KilometerErrorCode.ITEM_NOT_FOUND;
    }
}
