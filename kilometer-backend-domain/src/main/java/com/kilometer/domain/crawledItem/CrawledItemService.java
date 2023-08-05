package com.kilometer.domain.crawledItem;

import com.kilometer.domain.crawledItem.dto.CrawledItemDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class CrawledItemService {

    private CrawledItemRepository crawledItemRepository;

    @Transactional
    public void saveCrawledItem(CrawledItemDto crawledItemDto) {
        crawledItemRepository.save(crawledItemDto.toEntity());
    }
}
