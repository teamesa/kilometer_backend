package com.kilometer.domain.search;

import com.kilometer.domain.search.dto.SearchRequest;
import com.kilometer.domain.search.dto.SearchResponse;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    public SearchResponse search(SearchRequest searchRequest) {
        return SearchResponse.builder().build();
    }
}
