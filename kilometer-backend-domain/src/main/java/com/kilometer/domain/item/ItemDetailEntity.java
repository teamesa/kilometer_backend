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
@Table(name = "item_detail_entity")
public class ItemDetailEntity {

    @Id
    @GeneratedValue
    @Column(name = "detailId")
    private Long id;

    private String introduce;
}
