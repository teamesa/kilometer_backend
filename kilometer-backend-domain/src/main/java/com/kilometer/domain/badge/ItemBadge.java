package com.kilometer.domain.badge;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemBadge {
    private boolean isTypeBadge;
    private String text;
}
