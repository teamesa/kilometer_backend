package com.kilometer.backend.utils;

import com.kilometer.backend.controller.dto.ItemForm;
import com.kilometer.domain.file.S3Uploader;
import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.item.dto.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class FileUtils {

    private final S3Uploader s3Uploader;
    private final ItemService itemService;

    public String fileExists(MultipartFile image) throws IOException {
        String s3ImageUrl = "";
        String originalFilename = image.getOriginalFilename();
        if (StringUtils.hasText(originalFilename)) {
            s3ImageUrl = upload(image, "static");
        }
        return s3ImageUrl;
    }

    public String updateFileExists(Long itemId, ItemForm item) throws IOException {
        MultipartFile image = item.getImage();
        String imageUrl;
        String originalFilename = image.getOriginalFilename();
        if (StringUtils.hasText(originalFilename)) {
            imageUrl = upload(image, "static");
        } else {
            ItemResponse findItem = itemService.findOne(itemId);
            imageUrl = findItem.getImage();
        }
        return imageUrl;
    }

    private String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return s3Uploader.upload(uploadFile, dirName);
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        boolean newFile = convertFile.createNewFile();
        if(newFile) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }
}
