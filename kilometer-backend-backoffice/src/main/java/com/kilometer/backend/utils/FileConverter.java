package com.kilometer.backend.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FileConverter {

    public String convert(MultipartFile image) {
        return image.getOriginalFilename();
    }
}
