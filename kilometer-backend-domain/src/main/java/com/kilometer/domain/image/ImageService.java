package com.kilometer.domain.image;

import com.kilometer.domain.util.S3Uploader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Uploader s3Uploader;

    public String upload(byte[] file, String fileName, long maxFileSize) throws IOException {
        return s3Uploader.upload(file, fileName);
    }
}
