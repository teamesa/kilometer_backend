package com.kilometer.domain.homeModules.keyVisual.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyVisualUpdateResponse {

    private Long id;
    private String imageUrl;
    private String upperTitle;
    private String lowerTitle;
    private String linkUrl;
}
