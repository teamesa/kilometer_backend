package com.kilometer.domain.homeModules.modules.keyVisual.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class KeyVisualResponse {

    private Long id;
    private String imageUrl;
    private String upperTitle;
    private String lowerTitle;
    private String linkUrl;
    private String createdAccount;
    private LocalDateTime createdAt;
}
