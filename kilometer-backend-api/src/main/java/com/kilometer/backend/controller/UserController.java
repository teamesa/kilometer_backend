package com.kilometer.backend.controller;

import com.kilometer.backend.security.exception.ResourceNotFoundException;
import com.kilometer.backend.security.security.CurrentUser;
import com.kilometer.backend.security.security.UserPrincipal;
import com.kilometer.domain.user.dto.UserResponse;
import com.kilometer.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserResponse getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userService.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }
}
