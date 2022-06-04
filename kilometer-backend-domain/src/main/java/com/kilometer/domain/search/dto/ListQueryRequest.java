package com.kilometer.domain.search.dto;

import com.kilometer.domain.search.request.FilterOptions;
import com.kilometer.domain.search.request.SearchSortType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
@Builder
public class ListQueryRequest {
    private Pageable pageable;
    private FilterOptions filterOptions;
    private SearchSortType searchSortType;
    private long userId;
}
