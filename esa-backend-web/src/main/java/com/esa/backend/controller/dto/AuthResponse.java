package com.esa.backend.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {
    private final String accessToken;
    @Builder.Default
    private final String tokenType = "Bearer";
}