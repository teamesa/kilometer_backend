package com.kilometer.domain.image;

import com.kilometer.domain.util.S3Uploader;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final S3Uploader s3Uploader;

    public String upload(MultipartFile file) throws IOException {
        return s3Uploader.upload(file, "static");
    }
}
