package com.kilometer.domain.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_detail_image")
public class ItemDetailImage {

    @Id @GeneratedValue
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item")
    private ItemEntity item;

    public static ItemDetailImage makeEntity(String imageUrl) {
        return ItemDetailImage.builder()
            .imageUrl(imageUrl)
            .build();
    }

    public void setItemEntity(ItemEntity item) {
        this.item = item;
    }
}
