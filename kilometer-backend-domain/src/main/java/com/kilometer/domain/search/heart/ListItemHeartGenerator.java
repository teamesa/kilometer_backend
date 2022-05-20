package com.kilometer.domain.search.heart;

import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.util.ApiUrlUtils;
import org.springframework.stereotype.Component;

@Component
public class ListItemHeartGenerator {
    public ListItemHeart generateListItemHeart(ItemResponse item) {
        return ListItemHeart.builder()
                .heartClicked(false)
                .link(ApiUrlUtils.getPickItemUrl(item.getId(), true))
                .build();
    }
}
