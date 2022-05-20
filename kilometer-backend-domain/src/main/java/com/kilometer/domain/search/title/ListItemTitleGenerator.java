package com.kilometer.domain.search.title;

import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.util.FrontUrlUtils;
import org.springframework.stereotype.Component;

@Component
public class ListItemTitleGenerator {
    public ListItemTitle generateListItemTitle(ItemResponse item) {
        return ListItemTitle.builder()
                .text(item.getTitle())
                .link(FrontUrlUtils.getFrontDetailUrlPattern(item.getId()))
                .build();
    }
}
