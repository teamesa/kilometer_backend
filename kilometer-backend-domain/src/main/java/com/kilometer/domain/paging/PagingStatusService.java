package com.kilometer.domain.paging;

import com.kilometer.domain.archive.dto.ArchiveSortType;
import com.kilometer.domain.search.request.SearchRequest;
import com.kilometer.domain.search.request.SearchSortType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@SuppressWarnings("rawtypes")
public class PagingStatusService {
    public static final int DEFAULT_PAGE_SIZE = 10;

    private static final int HAS_NOT_NEXT_PAGE_NUMBER = -1;
    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final Sort DEFAULT_SORT_OPTION = SearchSortType.ENROLL_DESC.getSearchSortOption();

    public ResponsePagingStatus convert(Page page, String query) {
        return ResponsePagingStatus.builder()
                .currentPage(page.getNumber())
                .totalContentsCount(page.getTotalElements())
                .currentContentsCount(getCurrentContentsCount(page))
                .pageSize(page.getNumber())
                .nextPage(getNextPage(page))
                .hasNext(page.hasNext())
                .query(query)
                .build();
    }

    public ResponsePagingStatus convert(Page page) {
        return ResponsePagingStatus.builder()
                .currentPage(page.getNumber())
                .totalContentsCount(page.getTotalElements())
                .currentContentsCount(getCurrentContentsCount(page))
                .pageSize(page.getNumber())
                .nextPage(getNextPage(page))
                .hasNext(page.hasNext())
                .build();
    }

    public Pageable makePageable(SearchRequest searchRequest) {
        int page = Optional.ofNullable(searchRequest)
                .map(SearchRequest::getRequestPagingStatus)
                .map(RequestPagingStatus::getPageNumber)
                .orElse(DEFAULT_PAGE_NUMBER);
        int size = Optional.ofNullable(searchRequest)
                .map(SearchRequest::getRequestPagingStatus)
                .map(RequestPagingStatus::getPageSize)
                .orElse(DEFAULT_PAGE_SIZE);
        Sort sort = Optional.ofNullable(searchRequest)
                .map(SearchRequest::getSearchSortType)
                .map(SearchSortType::getSearchSortOption)
                .orElse(DEFAULT_SORT_OPTION);

        return PageRequest.of(page, size, sort);
    }

    public Pageable makePageable(RequestPagingStatus requestPagingStatus, ArchiveSortType sortType) {
        int page = getPage(requestPagingStatus);

        int size = getSize(requestPagingStatus);

        Sort sort = Optional.ofNullable(sortType)
                .map(ArchiveSortType::getArchiveSortOption)
                .orElse(DEFAULT_SORT_OPTION);

        return PageRequest.of(page, size, sort);
    }

    public Pageable makePageable(RequestPagingStatus requestPagingStatus) {
        int page = getPage(requestPagingStatus);

        int size = getSize(requestPagingStatus);

        return PageRequest.of(page, size);
    }

    private int getCurrentContentsCount(Page page) {
        if (page.hasNext()) {
            return (page.getNumber() + 1) * page.getSize();
        } else {
            return page.getNumber() * page.getSize() + page.getNumberOfElements();
        }
    }

    private int getNextPage(Page page) {
        if (page.hasNext()) {
            return page.nextPageable().getPageNumber();
        } else {
            return HAS_NOT_NEXT_PAGE_NUMBER;
        }
    }

    private Integer getPage(RequestPagingStatus requestPagingStatus) {
        return Optional.ofNullable(requestPagingStatus)
                .map(RequestPagingStatus::getPageNumber)
                .orElse(DEFAULT_PAGE_NUMBER);
    }

    private Integer getSize(RequestPagingStatus requestPagingStatus) {
        return Optional.ofNullable(requestPagingStatus)
                .map(RequestPagingStatus::getPageSize)
                .orElse(DEFAULT_PAGE_SIZE);
    }
}
