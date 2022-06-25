package com.kilometer.domain.item.heart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ItemHeart {
    private boolean heartClicked;
    private String link;
    private long id;
}
