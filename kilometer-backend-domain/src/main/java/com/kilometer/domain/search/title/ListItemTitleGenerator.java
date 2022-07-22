package com.kilometer.domain.search.title;

import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.pick.Pick;
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

    public ListItemTitle generateListItemTitle(Pick item) {
        return ListItemTitle.builder()
                .text(item.getPickedItem().getTitle())
                .link(FrontUrlUtils.getFrontDetailUrlPattern(item.getPickedItem().getId()))
                .build();
    }
}
