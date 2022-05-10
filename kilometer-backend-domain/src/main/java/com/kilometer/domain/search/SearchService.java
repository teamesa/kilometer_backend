package com.kilometer.domain.search;

import com.kilometer.domain.item.ItemService;
import com.kilometer.domain.item.dto.ItemResponse;
import com.kilometer.domain.search.dto.ListItem;
import com.kilometer.domain.search.dto.SearchRequest;
import com.kilometer.domain.search.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final ItemService itemService;

    public SearchResponse search(SearchRequest searchRequest) {
        Preconditions.notNull(searchRequest, String.format("this service can not be run will null object, please check this, %s", searchRequest));

        Pageable pageable = makePageable(searchRequest);
        Page<ItemResponse> pageableItems = itemService.findByDefaultPageable(pageable);
        return convertingItems(pageableItems);
    }

    private Pageable makePageable(SearchRequest searchRequest) {
        int page = searchRequest.getPage();
        int size = searchRequest.getPageSize();
//        Sort.Direction direction = Sort.Direction.DESC;

        return PageRequest.of(page, size);
    }

    private SearchResponse convertingItems(Page<ItemResponse> pageableItems) {
        List<ListItem> items = pageableItems.map(ListItem::makeSearchItem).getContent();
        return SearchResponse.builder()
                .currentPage(pageableItems.getNumber())
                .nextPage(pageableItems.hasNext() ? pageableItems.nextPageable().getPageNumber() : pageableItems.getNumber())
                .hasNext(pageableItems.hasNext())
                .pageSize(pageableItems.getSize())
                .items(items)
                .build();
    }

}
