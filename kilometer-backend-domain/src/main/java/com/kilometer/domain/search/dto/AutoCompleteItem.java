package com.kilometer.domain.search.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AutoCompleteItem {
    private Long id;
    private String title;
    private Integer searchedTextLocationStart;
    private Integer searchedTextLocationEnd;
    private String listImageUrl;
    private String link;
}
