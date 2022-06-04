package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.search.dto.ListQueryRequest;
import org.springframework.data.domain.Page;

public interface ItemRepositoryCustom {
    Page<SearchItemResponse> findAllBySortOption(ListQueryRequest queryRequest);
}
