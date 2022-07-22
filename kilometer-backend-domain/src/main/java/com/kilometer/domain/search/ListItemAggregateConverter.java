package com.kilometer.domain.search;

import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.item.heart.ItemHeart;
import com.kilometer.domain.item.heart.ItemHeartGenerator;
import com.kilometer.domain.pick.Pick;
import com.kilometer.domain.search.additionalinfo.ListItemAdditionalInfo;
import com.kilometer.domain.search.additionalinfo.ListItemAdditionalInfoGenerator;
import com.kilometer.domain.badge.ItemBadge;
import com.kilometer.domain.badge.ItemBadgeGenerator;
import com.kilometer.domain.search.dto.ListItem;
import com.kilometer.domain.search.presentationimage.PresentationImage;
import com.kilometer.domain.search.presentationimage.PresentationImageGenerator;
import com.kilometer.domain.search.title.ListItemTitle;
import com.kilometer.domain.search.title.ListItemTitleGenerator;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListItemAggregateConverter {

    private final PresentationImageGenerator presentationImageGenerator;
    private final ItemBadgeGenerator badgeGenerator;
    private final ListItemAdditionalInfoGenerator additionalInfoGenerator;
    private final ListItemTitleGenerator titleGenerator;
    private final ItemHeartGenerator heartGenerator;

    public ListItem convert(SearchItemResponse item) {
        Preconditions.notNull(item, String.format(
            "converting can not be run will null item response, please check this, %s", item));
        PresentationImage image = presentationImageGenerator.generatePresentationImage(item);
        ItemBadge typeBadge = badgeGenerator.generateTypeItemBadge(item);
        List<ItemBadge> additionalBadgeList = badgeGenerator.generateAdditionalItemBadgeList(
            item);
        ListItemAdditionalInfo listItemAdditionalInfo = additionalInfoGenerator.generateListItemAdditionalInfo(
            item);
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

    public ListItem convert(Pick item) {
        Preconditions.notNull(item, String.format(
                "converting can not be run will null item response, please check this, %s", item));
        PresentationImage image = presentationImageGenerator.generatePresentationImage(item);
        ItemBadge typeBadge = badgeGenerator.generateTypeItemBadge(item.getPickedItem().getExhibitionType());
        List<ItemBadge> additionalBadgeList = badgeGenerator.generateAdditionalItemBadgeList(item);
        ListItemTitle title = titleGenerator.generateListItemTitle(item);
        ItemHeart heart = heartGenerator.generateItemHeart(item);

        return ListItem.builder()
                .id(item.getId())
                .presentationImage(image)
                .typeBadge(typeBadge)
                .additionalBadgeList(additionalBadgeList)
                .title(title)
                .heart(heart)
                .build();
    }
}
