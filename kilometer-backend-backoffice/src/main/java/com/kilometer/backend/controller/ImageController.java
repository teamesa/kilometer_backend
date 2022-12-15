package com.kilometer.backend.controller;

import com.kilometer.domain.image.ImageService;
import com.kilometer.domain.util.BoUrlUtils;
import com.kilometer.domain.util.FileUtils;
import java.io.IOException;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(BoUrlUtils.IMAGE_ROOT)
public class ImageController {

    private final ImageService imageService;

    @Value("${cloud.aws.s3.folderName}")
    private String S3FolderName;

    @PostMapping(BoUrlUtils.IMAGE_DEFAULT)
    public String upload(MultipartFile file) throws IOException {
        return imageService.upload(file.getBytes(), file.getOriginalFilename(), S3FolderName,
            FileUtils.DEFAULT_IMAGE_SIZE);
    }

    @PostMapping(BoUrlUtils.IMAGE_LIST)
    public String uploadListImage(MultipartFile file) throws IOException {
        return imageService.upload(file.getBytes(), file.getOriginalFilename(), S3FolderName,
            FileUtils.LIST_IMAGE_SIZE);
    }

}
