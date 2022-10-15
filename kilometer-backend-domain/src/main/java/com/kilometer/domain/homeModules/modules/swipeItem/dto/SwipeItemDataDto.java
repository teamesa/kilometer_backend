package com.kilometer.domain.homeModules.modules.swipeItem.dto;

import com.kilometer.domain.linkInfo.LinkInfo;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SwipeItemDataDto {

    private LinkInfo title;
    private LinkInfo content;
    private String thumbnailPhotoUrl;
    private List<String> photoUrls;
    private List<String> keywords;

    public static SwipeItemDataDto of(String link, String title, String content,
        String thumbnailUrl, List<String> photoUrls, List<String> keywords) {
        return new SwipeItemDataDto(LinkInfo.of(title, link), LinkInfo.of(content, link),
            thumbnailUrl, photoUrls, keywords);
    }
}
