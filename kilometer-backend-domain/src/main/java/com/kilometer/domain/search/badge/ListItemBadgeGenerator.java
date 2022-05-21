package com.kilometer.domain.search.badge;

import com.kilometer.domain.item.FeeType;
import com.kilometer.domain.item.dto.SearchItemResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ListItemBadgeGenerator {
    private static final String ONGOING_PROGRESS_TYPE_TEXT = "ON";
    private static final String END_PROGRESS_TYPE_TEXT = "OFF";
    private static final String UPCOMING_PROGRESS_TYPE_TEXT = "UPCOMING";

    public ListItemBadge generateTypeListItemBadge(SearchItemResponse itemResponse) {
        return ListItemBadge.builder()
                .isTypeBadge(true)
                .text(itemResponse.getExhibitionType().getDescription())
                .build();
    }

    public List<ListItemBadge> generateAdditionalItemBadgeList(SearchItemResponse itemResponse) {
        ListItemBadge progressTypeBadge = makeProgressTypeBadge(itemResponse.getStartDate(), itemResponse.getEndDate());
        ListItemBadge feeTypeBadge = makeFeeTypeBadge(itemResponse.getFee());

        return List.of(progressTypeBadge, feeTypeBadge);
    }

    private ListItemBadge makeProgressTypeBadge(LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();

        String text;
        if (now.isBefore(startDate)) {
            text = UPCOMING_PROGRESS_TYPE_TEXT;
        } else if (now.isAfter(endDate)) {
            text = END_PROGRESS_TYPE_TEXT;
        } else {
            text = ONGOING_PROGRESS_TYPE_TEXT;
        }

        return ListItemBadge.builder()
                .isTypeBadge(false)
                .text(text)
                .build();
    }

    private ListItemBadge makeFeeTypeBadge(FeeType feeType) {
        return ListItemBadge.builder()
                .isTypeBadge(false)
                .text(feeType.getDescription())
                .build();
    }
}
