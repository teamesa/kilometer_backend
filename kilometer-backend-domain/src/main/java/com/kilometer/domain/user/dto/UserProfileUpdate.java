package com.kilometer.domain.user.dto;

import com.kilometer.domain.util.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserProfileUpdate {
    private long userId;
    private byte[] file;
    private String fileName;
    private String folderName;
    @Builder.Default
    private long maxFileSize = FileUtils.DEFAULT_IMAGE_SIZE;
}
