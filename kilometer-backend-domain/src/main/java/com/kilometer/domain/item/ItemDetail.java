package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.DetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.EAGER;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_detail")
public class ItemDetail {

    @Id
    @GeneratedValue
    private Long id;

    private String introduce;

    @OneToMany(mappedBy = "itemDetailEntity", fetch = EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @Builder.Default
    private List<DetailImage> images = new ArrayList<>();

    public DetailResponse makeResponse() {
        List<String> photo = images.stream().map(DetailImage::getUrl).collect(Collectors.toList());
        return DetailResponse.builder()
                .summary(introduce)
                .photo(photo)
                .build();
    }
}