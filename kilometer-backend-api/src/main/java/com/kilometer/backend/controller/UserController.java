package com.kilometer.backend.controller;

import com.google.common.base.Preconditions;
import com.kilometer.backend.security.exception.ResourceNotFoundException;
import com.kilometer.domain.user.UserService;
import com.kilometer.domain.user.dto.UserProfileUpdate;
import com.kilometer.domain.user.dto.UserResponse;
import com.kilometer.domain.user.dto.UserUpdateRequest;
import com.kilometer.domain.util.ApiUrlUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.kilometer.backend.security.security.SecurityUtils.getLoginUserId;

@RequiredArgsConstructor
@RestController()
public class UserController {
    private final UserService userService;

    @Value("${cloud.aws.s3.folderName}")
    private String S3FolderName;

    @GetMapping(ApiUrlUtils.USER_ME)
    @ApiOperation(value = "현재 로그인 된 유저 정보 조회")
    public UserResponse getCurrentUser() {
        return userService.findById(getLoginUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", getLoginUserId()));
    }

    @PostMapping(ApiUrlUtils.USER_ROOT)
    @ApiOperation(value = "유저 정보 변경 API")
    public UserResponse get(@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(userUpdateRequest)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", getLoginUserId()));
    }

    @PutMapping(ApiUrlUtils.USER_PROFILE)
    @ApiOperation(value = "유저 이미지 변경 API")
    public UserResponse upload(MultipartFile file) throws IOException {
        Preconditions.checkNotNull(file, String.format("File must not be null, userId: %s", getLoginUserId()));
        UserProfileUpdate request = UserProfileUpdate.builder()
                .fileName(file.getOriginalFilename())
                .userId(getLoginUserId())
                .file(file.getBytes())
                .folderName(S3FolderName)
                .build();
        return userService.updateUserProfile(request).orElseThrow(() -> new ResourceNotFoundException("User", "id", getLoginUserId()));
    }
}
