package com.kilometer.backend.controller;

import com.kilometer.backend.security.exception.ResourceNotFoundException;
import com.kilometer.domain.user.dto.UserResponse;
import com.kilometer.domain.user.UserService;
import com.kilometer.domain.user.dto.UserUpdateRequest;
import com.kilometer.domain.util.ApiUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.kilometer.backend.security.security.SecurityUtils.getLoginUserId;

@RequiredArgsConstructor
@RestController()
@RequestMapping(ApiUrlUtils.USER_ROOT)
public class UserController {
    private final UserService userService;

    @GetMapping(ApiUrlUtils.USER_ME)
    public UserResponse getCurrentUser() {
        return userService.findById(getLoginUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", getLoginUserId()));
    }

    @PostMapping()
    public UserResponse get(@RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.updateUser(userUpdateRequest)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", getLoginUserId()));
    }
}
