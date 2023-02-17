package com.kilometer.domain.item.itemDetail;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ItemDetailTest {

    @Test
    @DisplayName("아이템 디테일을 생성한다.")
    void create_itemDetail() {
        // given & when
        ItemDetail itemDetail = ItemDetail.builder()
            .introduce("소개 글")
            .source("출처")
            .build();

        // then
        assertThat(itemDetail).isInstanceOf(ItemDetail.class);
    }
}
