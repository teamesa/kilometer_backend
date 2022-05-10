package com.kilometer.domain.search.dto;

import com.kilometer.domain.item.dto.ItemResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import static com.kilometer.domain.search.dto.SearchDtoUtil.DEFAULT_ITEM_TYPE;

@Data
@Builder
@AllArgsConstructor
public class ListItem {
    private PresentationImage presentationImage;
    @Builder.Default
    private ListItemBadge typeBadge = makeDefaultTypeBadge();
    @Builder.Default
    private List<ListItemBadge> additionalBadgeList = List.of();
    private String title;
    private boolean isHearted;
    private ListItemAdditionalInfo listItemAdditionalInfo;

    public static ListItem makeSearchItem(ItemResponse item) {
        return ListItem.builder()
                .presentationImage(
                        PresentationImage.builder()
                                .url(item.getUrl())
                                .description(null)
                                .isDimTarget(false)
                                .build()
                )
                .typeBadge(
                        ListItemBadge.builder()
                                .isTypeBadge(true)
                                .text(item.getExhibitionType().getDescription())
                                .build()
                )
                .additionalBadgeList(List.of())
                .title(item.getTitle())
                .isHearted(false)
                .listItemAdditionalInfo(
                        ListItemAdditionalInfo.builder()
                                .heartCount(10)
                                .grade(5)
                                .archiveCount(10)
                                .build()
                )
                .build();
    }

    private static ListItemBadge makeDefaultTypeBadge() {
        return ListItemBadge.builder().isTypeBadge(true).text(DEFAULT_ITEM_TYPE).build();
    }
}
