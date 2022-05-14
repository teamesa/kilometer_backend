package com.kilometer.domain.search;

import com.kilometer.domain.search.request.SearchSortType;
import org.springframework.data.domain.Sort;

public final class SearchDtoConstants {
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final Sort DEFAULT_SORT_OPTION = SearchSortType.ENROLL_DESC.getSearchSortOption();
}
