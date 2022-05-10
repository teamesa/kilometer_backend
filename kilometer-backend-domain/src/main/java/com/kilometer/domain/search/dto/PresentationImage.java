package com.kilometer.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PresentationImage {
    private String url;
    private boolean isDimTarget;
    private String description;
}
