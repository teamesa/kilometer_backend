package com.kilometer.domain.search.dto;

import com.kilometer.domain.item.heart.ItemHeart;
import com.kilometer.domain.search.additionalinfo.ListItemAdditionalInfo;
import com.kilometer.domain.search.badge.ListItemBadge;
import com.kilometer.domain.search.presentationimage.PresentationImage;
import com.kilometer.domain.search.title.ListItemTitle;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ListItem {

    private long id;
    private PresentationImage presentationImage;
    private ListItemBadge typeBadge;
    @Builder.Default
    private List<ListItemBadge> additionalBadgeList = List.of();
    private ListItemTitle title;
    private ItemHeart heart;
    private ListItemAdditionalInfo listItemAdditionalInfo;
}
