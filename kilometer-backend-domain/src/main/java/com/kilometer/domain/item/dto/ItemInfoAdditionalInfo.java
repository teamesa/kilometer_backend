package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.heart.ItemHeart;
import com.kilometer.domain.linkInfo.LinkInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemInfoAdditionalInfo {

    private int heartCount;
    private ItemHeart heart;
    private LinkInfo archiveLink;

}
