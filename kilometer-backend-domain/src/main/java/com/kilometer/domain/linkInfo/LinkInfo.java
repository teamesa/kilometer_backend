package com.kilometer.domain.linkInfo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LinkInfo {
    private String title;
    private String link;

    public static LinkInfo of(String title, String link) {
        return LinkInfo.builder()
            .title(title)
            .link(link)
            .build();
    }
}
