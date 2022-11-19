package com.kilometer.domain.homeModules.modules.swipeItem.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SwipeItemDataDto {

    private LinkInfoDto title;
    private LinkInfoDto content;
    private String thumbnailPhotoUrl;
    private List<String> photoUrls;
    private List<String> keywords;

    public static SwipeItemDataDto of(String link, String text, String content,
        String thumbnailUrl, List<String> photoUrls, List<String> keywords) {
        return new SwipeItemDataDto(LinkInfoDto.of(text, link), LinkInfoDto.of(content, link),
            thumbnailUrl, photoUrls, keywords);
    }
}
