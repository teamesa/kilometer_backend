package com.kilometer.domain.homeModules.modules.keyVisual.dto;

import com.kilometer.domain.homeModules.modules.keyVisual.KeyVisual;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KeyVisualDataDto {
    private String keyVisualPhotoUrl;
    private String upperTitle;
    private String lowerTitle;
    private String link;

    public static KeyVisualDataDto from(KeyVisual keyVisual) {
        return new KeyVisualDataDto(keyVisual.getImageUrl(), keyVisual.getUpperTitle(),
            keyVisual.getLowerTitle(), keyVisual.getLinkUrl());
    }
}
