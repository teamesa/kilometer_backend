package com.kilometer.domain.item;

import com.kilometer.domain.search.request.FilterOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<ItemEntity> findAllBySortOption(Pageable pageable, FilterOptions filterOptions);
}
