package com.kilometer.domain.item.itemDetail;

import static javax.persistence.FetchType.EAGER;

import com.kilometer.domain.item.ItemEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_detail")
public class ItemDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String introduce;

    @OneToOne(fetch = EAGER)
    @JoinColumn(name = "item")
    private ItemEntity item;

    public void update(String introduce) {
        this.introduce = introduce;
    }

    public void setItemEntity(ItemEntity item) {
        this.item = item;
        item.setItemDetail(this);
    }
}
