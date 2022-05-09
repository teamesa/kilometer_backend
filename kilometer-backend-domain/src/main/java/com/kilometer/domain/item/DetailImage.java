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
@Table(name = "detail_image")
public class DetailImage {

    @Id @GeneratedValue
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "itemDetailEntity")
    private ItemDetail itemDetailEntity;
}
