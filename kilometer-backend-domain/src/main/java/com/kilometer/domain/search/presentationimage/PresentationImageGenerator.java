package com.kilometer.domain.search.presentationimage;

import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.util.FrontUrlUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class PresentationImageGenerator {
    private static final String BACKGROUND_END_TEXT = "종료";
    private static final String BACKGROUND_D_DAT_COUNT_FORMAT = "D-%s";
    private static final String DIM_HEX_CODE = "#FFF";
    private static final Double DIM_OPACITY = 0.55;


    public PresentationImage generatePresentationImage(ItemResponse item) {
        LocalDate now = LocalDate.now();

        if (now.isBefore(item.getStartDate())) {
            return makeUpcomingImage(item.getId(), item.getUrl(), item.getStartDate());
        } else if (now.isAfter(item.getEndDate())) {
            return makeEndImage(item.getId(), item.getUrl());
        } else {
            return makeOngoingImage(item.getId(), item.getUrl());
        }
    }

    private PresentationImage makeUpcomingImage(long id, String url, LocalDate startDate) {
        LocalDate now = LocalDate.now();
        int periodDays = Period.between(now, startDate).getDays();

        return PresentationImage.builder()
                .url(url)
                .backgroundText(String.format(BACKGROUND_D_DAT_COUNT_FORMAT, periodDays))
                .isDimTarget(true)
                .dimColor(DIM_HEX_CODE)
                .opacity(DIM_OPACITY)
                .link(FrontUrlUtils.getFrontDetailUrlPattern(id))
                .build();
    }

    private PresentationImage makeOngoingImage(long id, String url) {
        return PresentationImage.builder()
                .url(url)
                .isDimTarget(false)
                .link(FrontUrlUtils.getFrontDetailUrlPattern(id))
                .build();
    }

    private PresentationImage makeEndImage(long id, String url) {
        return PresentationImage.builder()
                .url(url)
                .backgroundText(BACKGROUND_END_TEXT)
                .isDimTarget(true)
                .dimColor(DIM_HEX_CODE)
                .opacity(DIM_OPACITY)
                .link(FrontUrlUtils.getFrontDetailUrlPattern(id))
                .build();
    }
}
