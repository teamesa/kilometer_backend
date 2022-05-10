package com.kilometer.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

import static com.kilometer.domain.search.dto.SearchDtoUtil.DEFAULT_PAGE_SIZE;

@Data
@Builder
@AllArgsConstructor
public class SearchResponse {
    // Info for pagination
    private int nextPage;
    private int currentPage;
    @Builder.Default
    private int pageSize = DEFAULT_PAGE_SIZE;
    private boolean hasNext;

    @Builder.Default
    private List<ListItem> items = List.of();
}
