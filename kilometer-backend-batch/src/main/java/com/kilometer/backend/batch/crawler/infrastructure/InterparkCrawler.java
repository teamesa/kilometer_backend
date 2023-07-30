package com.kilometer.backend.batch.crawler.infrastructure;

import com.kilometer.backend.batch.crawler.domain.Crawler;
import com.kilometer.backend.batch.crawler.domain.dto.CrawledItemDto;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InterparkCrawler implements Crawler {

    @Value("${crawler.target.interpark.origin}")
    public String ORIGIN;

    @Value("#{'${crawler.target.interpark.category}'.split(',')}")
    private List<String> PERFORMANCE_CATEGORY_URLS;

    @Value("${crawler.selenium.remote-driver-url}")
    private String remoteDriverUrl;

    @Override
    public List<CrawledItemDto> generateItem() {
        PERFORMANCE_CATEGORY_URLS.parallelStream()
                .map(this::extractPerformanceDetailPagesUrls)
                .forEach(System.out::println);
        return null;
    }

    private List<String> extractPerformanceDetailPagesUrls(final String pageUrl) {
        ChromeDriver<List<String>> chromeDriver = new ChromeDriver<>(remoteDriverUrl);
        Function<String, List<String>> page = (pageSource) -> Jsoup.parse(pageSource)
                .getElementsByClass("list")
                .stream()
                .map(Element::children)
                .flatMap(Collection::stream)
                .filter(element -> !element.id().contains("On"))
                .map(element -> element.child(0).attr("href"))
                .collect(Collectors.toList());
        return chromeDriver.crawlUrl(pageUrl, page, Collections.emptyList())
                .orElse(Collections.emptyList());
    }
}
