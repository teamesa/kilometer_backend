package com.kilometer.domain.search;

import com.kilometer.domain.search.dto.SearchRequest;
import com.kilometer.domain.search.dto.SearchResponse;
import org.junit.platform.commons.util.Preconditions;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    public SearchResponse search(SearchRequest searchRequest) {
        Preconditions.notNull(searchRequest, String.format("this service can not be run will null object, please check this, %s", searchRequest));
        return SearchResponse.builder().build();
    }
}
