package com.kilometer.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import static com.kilometer.domain.search.dto.SearchDtoUtil.DEFAULT_PAGE_SIZE;

@Data
@Builder
@AllArgsConstructor
public class SearchRequest {
    private int page;
    @Builder.Default
    private int pageSize = DEFAULT_PAGE_SIZE;
}
