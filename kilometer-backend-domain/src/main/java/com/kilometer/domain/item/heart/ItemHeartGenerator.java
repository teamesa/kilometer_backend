package com.kilometer.domain.item.heart;

import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.pick.Pick;
import com.kilometer.domain.util.ApiUrlUtils;
import org.springframework.stereotype.Component;

@Component
public class ItemHeartGenerator {
    public ItemHeart generateItemHeart(SearchItemResponse item) {
        return ItemHeart.builder()
                .id(item.getId())
                .heartClicked(item.isHearted())
                .link(ApiUrlUtils.getPickItemUrl(item.getId()))
                .build();
    }

    public ItemHeart generateItemHeart(Pick item) {
        return ItemHeart.builder()
                .id(item.getPickedItem().getId())
                .heartClicked(item.isHearted())
                .link(ApiUrlUtils.getPickItemUrl(item.getPickedItem().getId()))
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
