package com.kilometer.domain.homeModules.modules.keyVisual.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

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

    public boolean isNotNull() {
        return StringUtils.hasText(imageUrl) || StringUtils.hasText(upperTitle)
                || StringUtils.hasText(lowerTitle) || StringUtils.hasText(linkUrl);
    }
}
