package com.kilometer.domain.badge;

import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.item.enumType.ExhibitionType;
import com.kilometer.domain.item.enumType.FeeType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ItemBadgeGenerator {

    private static final String ONGOING_PROGRESS_TYPE_TEXT = "ON";
    private static final String END_PROGRESS_TYPE_TEXT = "OFF";
    private static final String UPCOMING_PROGRESS_TYPE_TEXT = "UPCOMING";

    public ItemBadge generateTypeItemBadge(SearchItemResponse itemResponse) {
        return ItemBadge.builder()
            .isTypeBadge(true)
            .text(itemResponse.getExhibitionType().getDescription())
            .build();
    }

    public ItemBadge generateTypeItemBadge(ExhibitionType exhibitionType) {
        return ItemBadge.builder()
            .isTypeBadge(true)
            .text(exhibitionType.getDescription())
            .build();
    }

    public List<ItemBadge> generateAdditionalItemBadgeList(SearchItemResponse itemResponse) {
        ItemBadge progressTypeBadge = makeProgressTypeBadge(itemResponse.getStartDate(),
            itemResponse.getEndDate());
        ItemBadge feeTypeBadge = makeFeeTypeBadge(itemResponse.getFeeType());

        return List.of(progressTypeBadge, feeTypeBadge);
    }

    private ItemBadge makeProgressTypeBadge(LocalDate startDate, LocalDate endDate) {
        LocalDate now = LocalDate.now();

        String text;
        if (now.isBefore(startDate)) {
            text = UPCOMING_PROGRESS_TYPE_TEXT;
        } else if (now.isAfter(endDate)) {
            text = END_PROGRESS_TYPE_TEXT;
        } else {
            text = ONGOING_PROGRESS_TYPE_TEXT;
        }

        return ItemBadge.builder()
            .isTypeBadge(false)
            .text(text)
            .build();
    }

    private ItemBadge makeFeeTypeBadge(FeeType feeType) {
        return ItemBadge.builder()
            .isTypeBadge(false)
            .text(feeType.getDescription())
            .build();
    }
}
