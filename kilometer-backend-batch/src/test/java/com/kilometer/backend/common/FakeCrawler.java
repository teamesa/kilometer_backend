package com.kilometer.backend.common;

import com.kilometer.backend.batch.crawler.domain.Crawler;
import com.kilometer.domain.crawledItem.dto.CrawledItemDto;
import java.util.List;

public class FakeCrawler implements Crawler {

    @Override
    public List<CrawledItemDto> generateItem() {
        return List.of(Fixture.crawledItemDto);
    }
}
