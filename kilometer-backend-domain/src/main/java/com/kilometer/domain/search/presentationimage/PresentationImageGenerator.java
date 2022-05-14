package com.kilometer.domain.search.presentationimage;

import com.kilometer.domain.item.dto.ItemResponse;
import org.springframework.stereotype.Component;

@Component
public class PresentationImageGenerator {
    public PresentationImage generatePresentationImage(ItemResponse item) {
        return PresentationImage.builder()
                .url(item.getImage())
                .backgroundText(null)
                .isDimTarget(false)
                .dimColor("#FFF")
                .opacity(0.55)
                .build();
    }
}
