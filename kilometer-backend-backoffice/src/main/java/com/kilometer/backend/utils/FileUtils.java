package com.kilometer.backend.utils;

import com.kilometer.backend.controller.dto.ItemForm;
import com.kilometer.domain.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class FileUtils {

    private final S3Uploader s3Uploader;

    public String getS3ImageUrl(ItemForm item) throws IOException {
        String s3ImageUrl = "";
        if (checkReceivedFile(multipartFileToString(item.getImage()))) {
            s3ImageUrl = upload(item.getImage());
        }
        return s3ImageUrl;
    }

    public ArrayList<String> getS3MultiImageUrl(ItemForm item) throws IOException {
        ArrayList<String> s3MultiImageUrl = new ArrayList<>();
        for (int i = 0; i < item.getDetailImageUrl().size(); i++) {
            if (!checkReceivedFile(multipartFileToString(item.getDetailImageUrl().get(i)))) {
                continue;
            }
            s3MultiImageUrl.add(upload(item.getDetailImageUrl().get(i)));
        }
        return s3MultiImageUrl;
    }

    public ArrayList<Long> getDeleteImage(ItemForm item) {
        ArrayList<Long> deleteImage = new ArrayList<>();
        if (item.getDeleteImageIndex() != null && !item.getDeleteImageIndex().isEmpty()) {
            deleteImage.addAll(item.getDeleteImageIndex());
        }
        return deleteImage;
    }

    private String upload(MultipartFile file) throws IOException {
        return s3Uploader.upload(file.getBytes(),file.getOriginalFilename());
    }

    private String multipartFileToString(MultipartFile file) {
        return file.getOriginalFilename();
    }

    private boolean checkReceivedFile(String file) {
        if (StringUtils.hasText(file)) {
            return true;
        }
        return false;
    }
}
