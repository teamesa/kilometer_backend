package com.kilometer.domain.item.dto;

import com.kilometer.domain.badge.ItemBadge;

public class ItemForArchive {

    private final Long id;

    private final String title;

    // Item의 List image url
    private final String imageUrl;

    private final ItemBadge typeBadge;

    private ItemForArchive(Long id, String title, String imageUrl, ItemBadge typeBadge) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.typeBadge = typeBadge;
    }

    public static ItemForArchive of(Long id, String title, String listImageUrl, ItemBadge badge) {
        return new ItemForArchive(id, title, listImageUrl, badge);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ItemBadge getTypeBadge() {
        return typeBadge;
    }
}
