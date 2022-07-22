package com.kilometer.domain.search.presentationimage;

import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.pick.Pick;
import com.kilometer.domain.util.FrontUrlUtils;
import java.time.LocalDate;
import java.time.Period;
import org.springframework.stereotype.Component;

@Component
public class PresentationImageGenerator {

    private static final String BACKGROUND_END_TEXT = "종료";
    private static final String BACKGROUND_D_DAT_COUNT_FORMAT = "D-%s";
    private static final String DIM_HEX_CODE = "#000";
    private static final Double DIM_OPACITY = 0.55;


    public PresentationImage generatePresentationImage(SearchItemResponse item) {
        LocalDate now = LocalDate.now();

        if (now.isBefore(item.getStartDate())) {
            return makeUpcomingImage(item.getId(), item.getListImageUrl(), item.getStartDate());
        } else if (now.isAfter(item.getEndDate())) {
            return makeEndImage(item.getId(), item.getListImageUrl());
        } else {
            return makeOngoingImage(item.getId(), item.getListImageUrl());
        }
    }

    public PresentationImage generatePresentationImage(Pick item) {
        LocalDate now = LocalDate.now();

        if (now.isBefore(item.getPickedItem().getStartDate())) {
            return makeUpcomingImage(item.getPickedItem().getId(), item.getPickedItem().getListImageUrl(), item.getPickedItem().getStartDate());
        } else if (now.isAfter(item.getPickedItem().getEndDate())) {
            return makeEndImage(item.getPickedItem().getId(), item.getPickedItem().getListImageUrl());
        } else {
            return makeOngoingImage(item.getPickedItem().getId(), item.getPickedItem().getListImageUrl());
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
