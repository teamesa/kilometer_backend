package com.kilometer.domain.item;

import com.kilometer.domain.item.dto.ItemInfoDto;
import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.search.dto.AutoCompleteItem;
import com.kilometer.domain.search.dto.ListQueryRequest;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ItemRepositoryCustom {

    Page<SearchItemResponse> findAllBySortOption(ListQueryRequest queryRequest);

    Page<AutoCompleteItem> findTop10ByQuery(String query);

    Optional<ItemInfoDto> findInfoByItemIdAndUserId(Long itemId, Long userId);
}
