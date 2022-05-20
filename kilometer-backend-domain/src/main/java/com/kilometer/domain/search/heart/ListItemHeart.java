package com.kilometer.domain.search.heart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ListItemHeart {
    private boolean heartClicked;
    private String link;
}
