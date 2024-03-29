package com.kilometer.backend.batch.crawler.domain;

import com.kilometer.domain.crawledItem.dto.CrawledItemDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface Crawler {

    List<CrawledItemDto> generateItem();
}
