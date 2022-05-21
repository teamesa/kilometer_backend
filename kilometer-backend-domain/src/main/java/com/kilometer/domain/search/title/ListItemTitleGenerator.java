package com.kilometer.domain.search.title;

import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.util.FrontUrlUtils;
import org.springframework.stereotype.Component;

@Component
public class ListItemTitleGenerator {
    public ListItemTitle generateListItemTitle(SearchItemResponse item) {
        return ListItemTitle.builder()
                .text(item.getTitle())
                .link(FrontUrlUtils.getFrontDetailUrlPattern(item.getId()))
                .build();
    }
}
