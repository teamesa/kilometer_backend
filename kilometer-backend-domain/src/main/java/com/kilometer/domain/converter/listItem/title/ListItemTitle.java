package com.kilometer.domain.converter.listItem.title;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ListItemTitle {
    private String text;
    private String link;
}
