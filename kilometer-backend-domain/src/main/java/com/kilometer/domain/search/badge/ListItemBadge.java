package com.kilometer.domain.search.badge;

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
