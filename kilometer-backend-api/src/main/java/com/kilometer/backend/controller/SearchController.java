package com.kilometer.backend.controller;

import com.kilometer.domain.search.SearchService;
import com.kilometer.domain.search.dto.AutoCompleteResult;
import com.kilometer.domain.search.request.SearchRequest;
import com.kilometer.domain.search.dto.SearchResponse;
import com.kilometer.domain.util.ApiUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.kilometer.backend.security.security.SecurityUtils.getLoginUserId;

@RestController
@RequiredArgsConstructor
public class SearchController {
    final SearchService searchService;

    @PostMapping(ApiUrlUtils.SEARCH_ROOT)
    public SearchResponse search(@RequestBody SearchRequest searchRequest) {
        long userId = getLoginUserId();
        return searchService.search(searchRequest, userId);
    }

    @GetMapping(ApiUrlUtils.SEARCH_AUTO_COMPLETE)
    public AutoCompleteResult autoCompleteSearch(@RequestParam String query) {
        return searchService.autoComplete(query);
    }

}
