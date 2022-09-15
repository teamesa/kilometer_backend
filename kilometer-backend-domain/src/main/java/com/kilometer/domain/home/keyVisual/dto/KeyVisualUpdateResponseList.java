package com.kilometer.domain.home.keyVisual.dto;

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
