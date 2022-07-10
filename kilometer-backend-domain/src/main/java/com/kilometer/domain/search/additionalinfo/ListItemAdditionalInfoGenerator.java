package com.kilometer.domain.search.additionalinfo;

import com.kilometer.domain.item.dto.SearchItemResponse;
import org.springframework.stereotype.Component;

@Component
public class ListItemAdditionalInfoGenerator {
    public ListItemAdditionalInfo generateListItemAdditionalInfo(SearchItemResponse response) {
        return ListItemAdditionalInfo.builder()
                .heartCount(response.getPickCount())
                .grade(response.getAvgStarRating())
                .archiveCount(response.getArchiveCount())
                .build();
    }
}
