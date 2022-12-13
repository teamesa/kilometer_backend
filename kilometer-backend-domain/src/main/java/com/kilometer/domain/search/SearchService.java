package com.kilometer.domain.search;

import com.google.common.base.Preconditions;
import com.kilometer.domain.converter.listItem.ListItemAggregateConverter;
import com.kilometer.domain.converter.listItem.dto.ListItem;
import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.item.dto.SearchItemResponse;
import com.kilometer.domain.paging.PagingStatusService;

import com.kilometer.domain.search.dto.AutoCompleteItem;
import com.kilometer.domain.search.dto.AutoCompleteResult;
import com.kilometer.domain.search.dto.ListQueryRequest;
import com.kilometer.domain.search.dto.SearchResponse;
import com.kilometer.domain.search.request.SearchRequest;
import lombok.RequiredArgsConstructor;

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
        Preconditions.checkNotNull(searchRequest, String.format("this service can not be run will null object, please check this, %s", searchRequest));
        Preconditions.checkNotNull(searchRequest.getRequestPagingStatus(), String.format("this service can not be run will null object, please check this, %s", searchRequest));

        Pageable pageable = pagingStatusService.makePageable(searchRequest);
        ListQueryRequest queryRequest = ListQueryRequest.builder()
                .filterOptions(searchRequest.getFilterOptions())
                .searchSortType(searchRequest.getSearchSortType())
                .queryString(searchRequest.getQueryString())
                .pageable(pageable)
                .userId(userId)
                .build();

        Page<SearchItemResponse> pageableItems = itemService.getItemBySearchOptions(queryRequest);
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
