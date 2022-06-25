package com.kilometer.domain.item;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.kilometer.domain.item.dto.ItemDetailImages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Entity
@Builder
@Where(clause = "isDeleted=false")
@SQLDelete(sql = "UPDATE item_detail_image SET isDeleted=true where id=?")
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_detail_image")
public class ItemDetailImage {

    @Id
    @GeneratedValue
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item")
    private ItemEntity item;

    @Builder.Default
    private boolean isDeleted = false;

    public static ItemDetailImage makeEntity(String imageUrl) {
        return ItemDetailImage.builder()
            .imageUrl(imageUrl)
            .build();
    }

    public void setItemEntity(ItemEntity item) {
        this.item = item;
    }

    public ItemDetailImages getItemDetailImage() {
        return ItemDetailImages.builder()
                .id(this.id)
                .images(this.imageUrl)
                .build();
    }
}
