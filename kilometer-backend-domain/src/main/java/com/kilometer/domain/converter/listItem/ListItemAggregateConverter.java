package com.kilometer.domain.converter.listItem;

import com.kilometer.domain.badge.ItemBadge;
import com.kilometer.domain.badge.ItemBadgeGenerator;
import com.kilometer.domain.homeModules.modules.monthlyFreeTicket.dto.MonthlyFreeTicketEntityDto;
import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.item.heart.ItemHeart;
import com.kilometer.domain.item.heart.ItemHeartGenerator;
import com.kilometer.domain.converter.listItem.additionalinfo.ListItemAdditionalInfo;
import com.kilometer.domain.converter.listItem.additionalinfo.ListItemAdditionalInfoGenerator;
import com.kilometer.domain.converter.listItem.dto.ListItem;
import com.kilometer.domain.converter.listItem.presentationimage.PresentationImage;
import com.kilometer.domain.converter.listItem.presentationimage.PresentationImageGenerator;
import com.kilometer.domain.converter.listItem.title.ListItemTitle;
import com.kilometer.domain.converter.listItem.title.ListItemTitleGenerator;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ListItemAggregateConverter {

    private final PresentationImageGenerator presentationImageGenerator;
    private final ItemBadgeGenerator badgeGenerator;
    private final ListItemAdditionalInfoGenerator additionalInfoGenerator;
    private final ListItemTitleGenerator titleGenerator;
    private final ItemHeartGenerator heartGenerator;

    public ListItem convert(ItemInfoExtraction item) {
        Preconditions.notNull(item, String.format(
                "converting can not be run will null item response, please check this, %s", item));
        PresentationImage image = presentationImageGenerator.generatePresentationImage(item);
        ItemBadge typeBadge = badgeGenerator.generateTypeItemBadge(item);
        List<ItemBadge> additionalBadgeList = badgeGenerator.generateAdditionalItemBadgeList(item);
        ListItemAdditionalInfo listItemAdditionalInfo = getListItemAdditionalInfo(item);
        ListItemTitle title = titleGenerator.generateListItemTitle(item);
        ItemHeart heart = heartGenerator.generateItemHeart(item);

        return ListItem.builder()
                .id(item.getId())
                .presentationImage(image)
                .typeBadge(typeBadge)
                .additionalBadgeList(additionalBadgeList)
                .title(title)
                .heart(heart)
                .listItemAdditionalInfo(listItemAdditionalInfo)
                .build();
    }

    private ListItemAdditionalInfo getListItemAdditionalInfo(ItemInfoExtraction item) {
        ListItemAdditionalInfo listItemAdditionalInfo;
        if ("search".equals(item.getApiType())) {
            listItemAdditionalInfo = additionalInfoGenerator.generateListItemAdditionalInfo((SearchItemResponse) item);
        } else if ("monthlyFreeTicket".equals(item.getApiType())) {
            listItemAdditionalInfo = additionalInfoGenerator.generateListItemAdditionalInfo((MonthlyFreeTicketEntityDto) item);
        }else {
            listItemAdditionalInfo = null;
        }
        return listItemAdditionalInfo;
    }
}
