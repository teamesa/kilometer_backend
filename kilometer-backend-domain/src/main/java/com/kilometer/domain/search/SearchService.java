package com.kilometer.domain.search;

import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.item.dto.SearchItemResponse;
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

    public SearchResponse search(SearchRequest searchRequest, long userId) {
        Preconditions.notNull(searchRequest, String.format("this service can not be run will null object, please check this, %s", searchRequest));
        Preconditions.notNull(searchRequest, String.format("this service can not be run will null object, please check this, %s", searchRequest));
        Preconditions.notNull(searchRequest.getRequestPagingStatus(), String.format("this service can not be run will null object, please check this, %s", searchRequest));

        Pageable pageable = pagingStatusService.makePageable(searchRequest);
        Page<SearchItemResponse> pageableItems = itemService.getItemBySearchOptions(
                pageable,
                searchRequest.getFilterOptions(),
                userId,
                searchRequest.getQueryString()
        );
        return convertingItems(pageableItems, searchRequest.getQueryString());
    }

    private SearchResponse convertingItems(Page<SearchItemResponse> pageableItems, String query) {
        List<ListItem> items = pageableItems.map(listItemAggregateConverter::convert).getContent();
        return SearchResponse.builder()
                .contents(items)
                .responsePagingStatus(pagingStatusService.convert(pageableItems, query))
                .build();
    }

    public AutoCompleteResult autoComplete(String query) {
        Page<AutoCompleteItem> contents = itemService.getAutoCompleteItemByQuery(query);
        return AutoCompleteResult.builder()
                .contents(contents.getContent())
                .responsePagingStatus(pagingStatusService.convert(contents, query))
                .build();
    }
}
