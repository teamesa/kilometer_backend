package com.kilometer.domain.search;

import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.search.additionalinfo.ListItemAdditionalInfo;
import com.kilometer.domain.search.additionalinfo.ListItemAdditionalInfoGenerator;
import com.kilometer.domain.search.badge.ListItemBadge;
import com.kilometer.domain.search.badge.ListItemBadgeGenerator;
import com.kilometer.domain.search.dto.ListItem;
import com.kilometer.domain.search.presentationimage.PresentationImage;
import com.kilometer.domain.search.presentationimage.PresentationImageGenerator;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListItemAggregateConverter {
    private final PresentationImageGenerator presentationImageGenerator;
    private final ListItemBadgeGenerator badgeGenerator;
    private final ListItemAdditionalInfoGenerator additionalInfoGenerator;

    public ListItem convert(ItemResponse item) {
        Preconditions.notNull(item, String.format("converting can not be run will null item response, please check this, %s", item));
        PresentationImage image = presentationImageGenerator.generatePresentationImage(item);
        ListItemBadge typeBadge = badgeGenerator.generateTypeListItemBadge(item);
        List<ListItemBadge> additionalBadgeList = badgeGenerator.generateAdditionalItemBadgeList(item);
        ListItemAdditionalInfo listItemAdditionalInfo = additionalInfoGenerator.generateListItemAdditionalInfo(item);

        return ListItem.builder()
                .presentationImage(image)
                .typeBadge(typeBadge)
                .additionalBadgeList(additionalBadgeList)
                .title(item.getTitle())
                .setHearted(false)
                .listItemAdditionalInfo(listItemAdditionalInfo)
                .build();
    }
}
