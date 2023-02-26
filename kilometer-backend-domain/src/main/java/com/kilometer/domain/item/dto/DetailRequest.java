package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.itemDetail.ItemDetail;

public interface DetailRequest {
    String getIntroduce();
    String getSource();

    default ItemDetail toItemDetailEntity() {
        return ItemDetail.builder()
                .source(getSource())
                .introduce(getIntroduce())
                .build();
    }
}
