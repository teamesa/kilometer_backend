package com.kilometer.domain.search.additionalinfo;

import com.kilometer.domain.archive.generator.ArchiveRatingConverter;
import com.kilometer.domain.item.dto.SearchItemResponse;
import org.springframework.stereotype.Component;

@Component
public class ListItemAdditionalInfoGenerator {

    public ListItemAdditionalInfo generateListItemAdditionalInfo(SearchItemResponse response) {
        double archiveStarRating = ArchiveRatingConverter.convertArchiveRatingAverage(
            response.getAvgStarRating());
        return ListItemAdditionalInfo.builder()
            .heartCount(response.getPickCount())
            .grade(archiveStarRating)
            .archiveCount(response.getArchiveCount())
            .build();
    }
}
