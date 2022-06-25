package com.kilometer.domain.archive.heart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ArchiveHeart {
    private boolean heartClicked;
    private String link;
}
