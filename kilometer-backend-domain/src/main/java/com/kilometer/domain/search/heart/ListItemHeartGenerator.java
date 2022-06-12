package com.kilometer.domain.search.heart;

import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.util.ApiUrlUtils;
import org.springframework.stereotype.Component;

@Component
public class ListItemHeartGenerator {
    public ListItemHeart generateListItemHeart(SearchItemResponse item) {
        return ListItemHeart.builder()
                .id(item.getId())
                .heartClicked(item.isHearted())
                .link(ApiUrlUtils.getPickItemUrl(item.getId()))
                .build();
    }
}
