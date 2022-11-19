package com.kilometer.domain.homeModules.modules.keyVisual.dto;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class KeyVisualApiResponse {

    private List<KeyVisualDataDto> keyVisualDatas;

    public static KeyVisualApiResponse from(List<KeyVisualDataDto> dataDtos) {
        return new KeyVisualApiResponse(dataDtos);
    }
}
