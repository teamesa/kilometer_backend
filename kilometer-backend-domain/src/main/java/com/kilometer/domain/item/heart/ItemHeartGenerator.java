package com.kilometer.domain.item.heart;

import com.kilometer.domain.converter.listItem.ItemInfoExtraction;
import com.kilometer.domain.util.ApiUrlUtils;
import org.springframework.stereotype.Component;

@Component
public class ItemHeartGenerator {

    public ItemHeart generateItemHeart(ItemInfoExtraction item) {
        return ItemHeart.builder()
                .id(item.getId())
                .heartClicked(item.isHearted())
                .link(ApiUrlUtils.getPickItemUrl(item.getId()))
                .build();
    }

    public ItemHeart generateItemHeart(Long itemId, boolean isHearted) {
        return ItemHeart.builder()
            .id(itemId)
            .heartClicked(isHearted)
            .link(ApiUrlUtils.getPickItemUrl(itemId))
            .build();
    }
}
