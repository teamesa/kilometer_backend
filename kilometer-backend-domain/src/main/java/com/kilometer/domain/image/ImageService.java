package com.kilometer.domain.image;

import com.kilometer.domain.util.FileUtils;
import com.kilometer.domain.util.S3Uploader;
import java.io.IOException;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Uploader s3Uploader;

    public String upload(byte[] file, String fileName, String folderName, long maxFileSize)
        throws IOException {
        Preconditions.condition((file.length > 0 && file.length <= maxFileSize),
            "The file size out of range.");
        Preconditions.condition(StringUtils.hasText(fileName),
            "The file name must not be empty or null.");
        Preconditions.condition((fileName.length() > 0 && fileName.length() <= 256),
            "The file name must be between 1 and 256 characters in length");
        Preconditions.condition(checkPhotoFileExtension(fileName),
            "The file extension must be specified extension.");
        return s3Uploader.upload(file, fileName, folderName);
    }

    private boolean checkPhotoFileExtension(String fileName) {
        return Pattern.compile(FileUtils.PHOTO_FILE_EXTENSION_REGEX).matcher(fileName).find();
    }
}
