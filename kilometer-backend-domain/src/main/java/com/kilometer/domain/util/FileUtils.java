package com.kilometer.domain.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileUtils {

    public static final long DEFAULT_IMAGE_SIZE = 10485760L;
    public static final long LIST_IMAGE_SIZE = 307200L;
    public static final String S3_ROOT = "static";

    public static final String PHOTO_FILE_EXTENSION_REGEX = ".(png|PNG|jpeg|JPEG|jpg|JPG|gif|GIF)$";

    public static String getFilePath() {
        return S3_ROOT + "/" + LocalDate.now();
    }

    public static String getFileName(String originFileName) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")) + "-"
            + originFileName;
    }

}
