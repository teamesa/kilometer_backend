package com.kilometer.domain.search;

import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.paging.PagingStatusService;
import com.kilometer.domain.search.dto.*;
import com.kilometer.domain.search.request.SearchRequest;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ItemService itemService;
    private final ListItemAggregateConverter listItemAggregateConverter;
    private final PagingStatusService pagingStatusService;

    public SearchResponse search(SearchRequest searchRequest) {
        Preconditions.notNull(searchRequest, String.format("this service can not be run will null object, please check this, %s", searchRequest));Preconditions.notNull(searchRequest, String.format("this service can not be run will null object, please check this, %s", searchRequest));
        Preconditions.notNull(searchRequest.getRequestPagingStatus(), String.format("this service can not be run will null object, please check this, %s", searchRequest));

        Pageable pageable = pagingStatusService.makePageable(searchRequest);
        Page<ItemResponse> pageableItems = itemService.findByDefaultPageable(pageable, searchRequest.getFilterOptions());
        return convertingItems(pageableItems);
    }

    private SearchResponse convertingItems(Page<ItemResponse> pageableItems) {
        List<ListItem> items = pageableItems.map(listItemAggregateConverter::convert).getContent();
        return SearchResponse.builder()
                .contents(items)
                .responsePagingStatus(pagingStatusService.convert(pageableItems))
                .build();
    }

}
