package com.kilometer.domain.search.request;

import com.kilometer.domain.paging.RequestPagingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    private RequestPagingStatus requestPagingStatus = RequestPagingStatus.empty();
    private SearchSortType searchSortType = SearchSortType.ENROLL_DESC;
    private FilterOptions filterOptions;
    private String queryString;
}
