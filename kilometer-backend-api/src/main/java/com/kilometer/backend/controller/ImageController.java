package com.kilometer.backend.controller;

import com.google.common.base.Preconditions;
import com.kilometer.domain.image.ImageService;
import com.kilometer.domain.util.ApiUrlUtils;
import com.kilometer.domain.util.FileUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor

public class ImageController {

    private final ImageService imageService;

    @Value("${cloud.aws.s3.folderName}")
    private String S3FolderName;

    @PostMapping(ApiUrlUtils.IMAGE_ROOT)
    @ApiOperation(value = "이미지 등록 API")
    public String upload(MultipartFile file) throws IOException {
        Preconditions.checkNotNull(file, "File must not be null");
        return imageService.upload(file.getBytes(), file.getOriginalFilename(), S3FolderName,
            FileUtils.DEFAULT_IMAGE_SIZE);
    }
}
