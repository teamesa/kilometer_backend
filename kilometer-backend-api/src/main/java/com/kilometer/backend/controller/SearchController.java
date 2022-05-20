package com.kilometer.backend.controller;

import com.kilometer.domain.search.SearchService;
import com.kilometer.domain.search.request.SearchRequest;
import com.kilometer.domain.search.dto.SearchResponse;
import com.kilometer.domain.util.ApiUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiUrlUtils.SEARCH_ROOT)
public class SearchController {
    final SearchService searchService;

    @PostMapping
    public SearchResponse search(@RequestBody SearchRequest searchRequest) {
        return searchService.search(searchRequest);
    }

}
