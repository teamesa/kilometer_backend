package com.kilometer.domain.converter.listItem.title;

import com.kilometer.domain.converter.listItem.ItemInfoExtraction;
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
