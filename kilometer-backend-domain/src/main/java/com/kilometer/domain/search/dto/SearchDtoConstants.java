package com.kilometer.domain.search.dto;

import org.springframework.data.domain.Sort;

public final class SearchDtoConstants {
    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final Sort DEFAULT_SORT_OPTION = SearchSortType.ENROLL_DESC.getSearchSortOption();
    public static final String DEFAULT_ITEM_TYPE = "전시회";
}
