package com.kilometer.domain.search.presentationimage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class PresentationImage {
    private String url;
    private String link;
    private boolean isDimTarget;
    private String backgroundText;
    private String dimColor;
    private double opacity;
}
