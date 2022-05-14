package com.kilometer.domain.search.additionalinfo;

import com.kilometer.domain.item.dto.ItemResponse;
import org.springframework.stereotype.Component;

@Component
public class ListItemAdditionalInfoGenerator {
    public ListItemAdditionalInfo generateListItemAdditionalInfo(ItemResponse response) {
        return ListItemAdditionalInfo.builder()
                .heartCount(10)
                .grade(5)
                .archiveCount(10)
                .build();
    }
}
