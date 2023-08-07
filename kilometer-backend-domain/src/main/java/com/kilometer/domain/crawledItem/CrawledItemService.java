package com.kilometer.domain.crawledItem;

import com.kilometer.domain.crawledItem.dto.CrawledItemDto;
import com.kilometer.domain.crawledItem.dto.CrawledItemPageResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CrawledItemService {

    private static final int PAGE_SIZE = 20;

    private CrawledItemRepository crawledItemRepository;

    @Transactional
    public void saveCrawledItem(final CrawledItemDto crawledItemDto) {
        crawledItemRepository.save(crawledItemDto.toEntity());
    }

    public boolean hasDuplicatedCrawledItem(final CrawledItemDto crawledItemDto) {
        CrawledItem crawledItem = crawledItemDto.toEntity();
        return crawledItemRepository.findByRegionTypeAndPlaceNameAndStartDateAndTitle(crawledItem.getRegionType(),
                        crawledItem.getPlaceName(), crawledItem.getStartDate(), crawledItem.getTitle())
                .isPresent();
    }

    public CrawledItemPageResponse getCrawledItem(final int page) {
        Page<CrawledItem> currentPageOfCrawledItem = crawledItemRepository.findAll(PageRequest.of(page, PAGE_SIZE));
        int totalPages = currentPageOfCrawledItem.getTotalPages();
        List<CrawledItem> crawledItems = currentPageOfCrawledItem.stream()
                .collect(Collectors.toList());
        return CrawledItemPageResponse.from(totalPages, page, crawledItems);
    }
}
