package com.kilometer.domain.converter.listItem.additionalinfo;

import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketEntityDto;
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

    public ListItemAdditionalInfo generateListItemAdditionalInfo(MonthlyFreeTicketEntityDto response) {
        return ListItemAdditionalInfo.builder()
            .heartCount(response.getPickCount())
            .grade(response.getAvgStarRating())
            .archiveCount(response.getArchiveCount())
            .build();
    }
}
