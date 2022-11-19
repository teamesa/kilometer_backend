package com.kilometer.domain.homeModules.modules.swipeItem.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LinkInfoDto {
    private String text;
    private String link;

    public static LinkInfoDto of(String text, String link) {
        return new LinkInfoDto(text,link);
    }
}
