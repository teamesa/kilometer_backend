package com.kilometer.backend.controller;

import com.kilometer.domain.search.SearchService;
import com.kilometer.domain.search.dto.SearchRequest;
import com.kilometer.domain.search.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {
    final SearchService searchService;

    @GetMapping
    public SearchResponse search() {
        SearchRequest request = SearchRequest.builder().build();
        return searchService.search(request);
    }

}
