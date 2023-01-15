package com.kilometer.domain.item.dto;

import com.kilometer.domain.badge.ItemBadge;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemSummary {

    private Long id;

    private String title;

    // Item의 List image url
    private String imageUrl;

    private ItemBadge typeBadge;

    public static ItemSummary of(Long id, String title, String listImageUrl, ItemBadge badge) {
        return new ItemSummary(id, title, listImageUrl, badge);
    }
}
