package com.kilometer.domain.item.dto;

import com.kilometer.domain.item.ItemDetail;
import com.kilometer.domain.item.ItemDetailImage;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailResponse {

    private String summary;
    @Builder.Default
    private List<String> photo = List.of();

    public static DetailResponse empty() {
        return DetailResponse
            .builder()
            .build();
    }

    public static DetailResponse makeResponse(ItemDetail itemDetail,
        List<ItemDetailImage> itemDetailImages) {
        return DetailResponse.builder()
            .summary(Optional.ofNullable(itemDetail)
                .map(ItemDetail::getIntroduce)
                .orElse(null))
            .photo(Optional.ofNullable(itemDetailImages)
                .map(images -> images.stream()
                    .map(ItemDetailImage::getImageUrl)
                    .collect(Collectors.toList()))
                .orElse(List.of()))
            .build();
    }
}
