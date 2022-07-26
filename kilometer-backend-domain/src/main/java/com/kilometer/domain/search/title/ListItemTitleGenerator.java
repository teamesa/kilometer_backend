package com.kilometer.domain.search.title;

import com.kilometer.domain.search.ItemInfoExtraction;
import com.kilometer.domain.util.FrontUrlUtils;
import org.springframework.stereotype.Component;

@Component
public class ListItemTitleGenerator {

    public ListItemTitle generateListItemTitle(ItemInfoExtraction item) {
        return ListItemTitle.builder()
                .text(item.getTitle())
                .link(FrontUrlUtils.getFrontDetailUrlPattern(item.getId()))
                .build();
    }
}
