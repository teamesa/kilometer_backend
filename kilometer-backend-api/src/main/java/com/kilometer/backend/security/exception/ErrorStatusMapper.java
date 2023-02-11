package com.kilometer.backend.security.exception;

import static com.kilometer.exception.KilometerErrorCode.ARCHIVE_NOT_FOUND;
import static com.kilometer.exception.KilometerErrorCode.ARCHIVE_VALIDATION_EXCEPTION;
import static com.kilometer.exception.KilometerErrorCode.ITEM_EXPOSURE_FALSE;

import com.kilometer.exception.KilometerErrorCode;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorStatusMapper {

    private static final Map<KilometerErrorCode, HttpStatus> MAPPER = new HashMap<>();


    private ErrorStatusMapper() {
        MAPPER.put(ARCHIVE_NOT_FOUND, HttpStatus.NOT_FOUND);
        MAPPER.put(ARCHIVE_VALIDATION_EXCEPTION, HttpStatus.BAD_REQUEST);
        MAPPER.put(ITEM_EXPOSURE_FALSE, HttpStatus.NOT_FOUND);
    }

    public HttpStatus getStatus(KilometerErrorCode code) {
        return MAPPER.get(code);
    }
}
