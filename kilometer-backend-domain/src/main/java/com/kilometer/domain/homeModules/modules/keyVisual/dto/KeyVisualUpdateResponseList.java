package com.kilometer.domain.homeModules.modules.keyVisual.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class KeyVisualUpdateResponseList {

    private List<KeyVisualUpdateResponse> keyVisualList;
}
