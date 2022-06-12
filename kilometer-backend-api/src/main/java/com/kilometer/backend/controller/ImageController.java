package com.kilometer.backend.controller;

import com.kilometer.domain.image.ImageService;
import com.kilometer.domain.util.ApiUrlUtils;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrlUtils.IMAGE_ROOT)
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    @ApiOperation(value = "이미지 등록 API")
    public String upload(MultipartFile file) throws IOException {
        return imageService.upload(file);
    }
}
