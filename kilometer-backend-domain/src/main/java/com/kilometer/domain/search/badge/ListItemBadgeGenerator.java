package com.kilometer.domain.search.badge;

import com.kilometer.domain.item.dto.ItemResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListItemBadgeGenerator {
    public ListItemBadge generateTypeListItemBadge(ItemResponse itemResponse) {
        return ListItemBadge.builder()
                .isTypeBadge(true)
                .text(itemResponse.getExhibitionType().getDescription())
                .build();
    }

    public List<ListItemBadge> generateAdditionalItemBadgeList(ItemResponse itemResponse) {
        return List.of();
    }
}
