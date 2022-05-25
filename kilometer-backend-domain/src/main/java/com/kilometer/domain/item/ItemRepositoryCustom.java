package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.search.dto.AutoCompleteItem;
import com.kilometer.domain.search.request.FilterOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemRepositoryCustom {
    Page<SearchItemResponse> findAllBySortOption(Pageable pageable, FilterOptions filterOptions, long userId);
    List<AutoCompleteItem> findTop10ByQuery(String query);
}
