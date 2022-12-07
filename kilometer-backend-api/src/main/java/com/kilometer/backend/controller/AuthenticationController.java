package com.kilometer.backend.controller;

import com.kilometer.backend.service.authentication.AuthenticationService;
import com.kilometer.domain.user.dto.AuthRequest;
import com.kilometer.domain.user.dto.AuthResponse;
import com.kilometer.domain.util.ApiUrlUtils;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrlUtils.AUTHENTICATION_ROOT)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    @ApiOperation("유저 로그인 API")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authenticationService.authenticate(authRequest));
    }
}
