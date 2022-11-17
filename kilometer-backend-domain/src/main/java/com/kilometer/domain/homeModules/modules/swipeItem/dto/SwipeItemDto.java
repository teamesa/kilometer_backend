package com.kilometer.domain.homeModules.modules.swipeItem.dto;

import com.kilometer.domain.item.enumType.ExhibitionType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SwipeItemDto {
    private String title;
    private String content;
    private String placeName;
    private ExhibitionType exhibitionType;
    private String thumbnailImageUrl;
    private List<String> photos;

    public SwipeItemDto setPhotoUrls(List<String> photos) {
        this.photos = photos;
        return this;
    }
}
