package com.kilometer.domain.homeModules.modules.keyVisual.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KeyVisualUpdateResponseList {

    private List<KeyVisualUpdateResponse> keyVisualList;
}
