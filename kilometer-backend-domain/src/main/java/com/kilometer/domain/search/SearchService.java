package com.kilometer.domain.search;

import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.search.dto.*;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.kilometer.domain.search.dto.SearchDtoConstants.DEFAULT_PAGE_NUMBER;
import static com.kilometer.domain.search.dto.SearchDtoConstants.DEFAULT_SORT_OPTION;
import static com.kilometer.domain.search.dto.SearchDtoConstants.DEFAULT_PAGE_SIZE;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ItemService itemService;

    public SearchResponse search(SearchRequest searchRequest) {
        Preconditions.notNull(searchRequest, String.format("this service can not be run will null object, please check this, %s", searchRequest));
        Preconditions.notNull(searchRequest.getRequestPagingStatus(), String.format("this service can not be run will null object, please check this, %s", searchRequest));

        Pageable pageable = makePageable(searchRequest);
        Page<ItemResponse> pageableItems = itemService.findByDefaultPageable(pageable);
        return convertingItems(pageableItems);
    }

    private Pageable makePageable(SearchRequest searchRequest) {
        int page = Optional.ofNullable(searchRequest)
                .map(SearchRequest::getRequestPagingStatus)
                .map(ResponsePagingStatus::getCurrentPage)
                .orElse(DEFAULT_PAGE_NUMBER);
        int size = Optional.ofNullable(searchRequest)
                .map(SearchRequest::getRequestPagingStatus)
                .map(ResponsePagingStatus::getPageSize)
                .orElse(DEFAULT_PAGE_SIZE);
        Sort sort = Optional.ofNullable(searchRequest)
                .map(SearchRequest::getSearchSortType)
                .map(SearchSortType::getSearchSortOption)
                .orElse(DEFAULT_SORT_OPTION);

        return PageRequest.of(page, size, sort);
    }

    private SearchResponse convertingItems(Page<ItemResponse> pageableItems) {
        List<ListItem> items = pageableItems.map(ListItem::makeSearchItem).getContent();
        return SearchResponse.builder()
                .contents(items)
                .build();
    }

}
