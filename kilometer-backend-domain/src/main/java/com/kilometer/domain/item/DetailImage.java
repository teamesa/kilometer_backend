package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.ItemDetailImages;
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
@Table(name = "detail_image")
public class DetailImage {

    @Id @GeneratedValue
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "itemDetailEntity")
    private ItemDetail itemDetailEntity;

    public static DetailImage makeEntity(String url) {
        return DetailImage.builder()
            .url(url)
            .build();
    }

    public static DetailImage makeEntity(String url, ItemDetail itemDetail) {
        return DetailImage.builder()
            .url(url)
            .itemDetailEntity(itemDetail)
            .build();
    }

    public ItemDetailImages getDetailImages() {
        return ItemDetailImages.builder()
                .id(this.id)
                .images(url)
                .build();
    }
}
