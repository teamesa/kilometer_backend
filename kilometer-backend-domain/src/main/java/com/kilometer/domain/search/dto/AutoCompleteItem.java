package com.kilometer.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AutoCompleteItem {
    private long id;
    private String title;
    private int searchedTextLocationStart;
    private int searchedTextLocationEnd;
    private String link;
}
