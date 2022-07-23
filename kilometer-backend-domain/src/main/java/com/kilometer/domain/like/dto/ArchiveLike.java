package com.kilometer.domain.like.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ArchiveLike {
    private boolean heartClicked;
    private String link;
}
