package com.kilometer.backend.controller;

import com.kilometer.backend.security.exception.ResourceNotFoundException;
import com.kilometer.domain.user.dto.UserResponse;
import com.kilometer.domain.user.UserService;
import com.kilometer.domain.user.dto.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.kilometer.backend.security.security.SecurityUtils.getLoginUserId;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/api/user/me")
    public UserResponse getCurrentUser() {
        return userService.findById(getLoginUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", getLoginUserId()));
    }

    @PostMapping("/api/user")
    public UserResponse get(@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(userUpdateRequest)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", getLoginUserId()));
    }
}
