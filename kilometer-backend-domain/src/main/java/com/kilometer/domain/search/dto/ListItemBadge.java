package com.kilometer.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ListItemBadge {
    private boolean isTypeBadge;
    private String text;
}
