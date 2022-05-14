package com.kilometer.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private ResponsePagingStatus requestPagingStatus = ResponsePagingStatus.empty();
    private SearchSortType searchSortType = SearchSortType.ENROLL_DESC;
    private FilterOptions filterOptions;
    private String queryString;
}
