package com.kilometer.domain.search.dto;

import com.kilometer.domain.search.additionalinfo.ListItemAdditionalInfo;
import com.kilometer.domain.search.badge.ListItemBadge;
import com.kilometer.domain.search.heart.ListItemHeart;
import com.kilometer.domain.search.presentationimage.PresentationImage;
import com.kilometer.domain.search.title.ListItemTitle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    private ListItemHeart heart;
    private ListItemAdditionalInfo listItemAdditionalInfo;
}
