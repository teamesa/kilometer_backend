package com.kilometer.backend.batch.crawler.infrastructure;

import com.kilometer.backend.batch.crawler.domain.Crawler;
import com.kilometer.domain.crawledItem.dto.CrawledItemDto;
import com.kilometer.backend.batch.crawler.util.ExhibitionTypeConverter;
import com.kilometer.backend.batch.crawler.util.Yes24FeeTypeConverter;
import com.kilometer.backend.batch.crawler.util.RegionTypeConverter;
import com.kilometer.domain.item.enumType.ExposureType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Yes24Crawler implements Crawler {

    public static final int PERFORMANCE_SCHEDULE_INCLUSIVE_MIN_LENGTH = 0;
    public static final int PERFORMANCE_SCHEDULE_EXCLUSIVE_MAX_LENGTH = 101;

    private static final String ITEM_DETAIL_IMAGE_XPATH = "//div[@id='divPerfContent']//p";
    private static final String NO_PERFORMANCE_DURATION = "-";
    public static final String PERFORMANCE_PERIOD_DELIMETER = " ~ ";

    @Value("${crawler.target.yes24.origin}")
    private String ORIGIN;

    @Value("#{'${crawler.target.yes24.category}'.split(',')}")
    private List<String> PERFORMANCE_CATEGORY_URLS;

    @Value("${crawler.selenium.remote-driver-url}")
    private String remoteDriverUrl;

    @Override
    public List<CrawledItemDto> generateItem() {
        return PERFORMANCE_CATEGORY_URLS.parallelStream()
                .map(this::extractPerformanceDetailPageUrls)
                .flatMap(Collection::stream)
                .map(this::extractItemDetail)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    private List<String> extractPerformanceDetailPageUrls(final String pageUrl) {
        ChromeDriver<List<String>> chromeDriver = new ChromeDriver<>(remoteDriverUrl);
        Function<String, List<String>> page = (pageSource) -> Jsoup.parse(pageSource)
                .getElementsByClass("m2-sec02")
                .get(0)
                .getElementsByTag("a")
                .stream()
                .map(aTag -> ORIGIN + aTag.attr("href"))
                .filter(performanceUrl -> performanceUrl.contains("/Perf"))
                .collect(Collectors.toList());
        return chromeDriver.crawlUrl(pageUrl, page, Collections.emptyList())
                .orElse(Collections.emptyList());
    }

    private Optional<CrawledItemDto> extractItemDetail(final String pageUrl) {
        ChromeDriver<CrawledItemDto> chromeDriver = new ChromeDriver<>(remoteDriverUrl);
        Function<String, CrawledItemDto> page = pageSource -> parsePage(pageSource, pageUrl);
        List<Function<WebDriver, ExpectedCondition<?>>> expectedRenderingConditions = List.of(
                (webDriver) -> ExpectedConditions.visibilityOfElementLocated(By.xpath(ITEM_DETAIL_IMAGE_XPATH)),
                (webDriver) -> ExpectedConditions.textToBePresentInElementLocated(ById.id("TheaterAddress"), " ")
        );
        return chromeDriver.crawlUrl(pageUrl, page, expectedRenderingConditions);
    }

    private CrawledItemDto parsePage(final String pageSource, final String pageUrl) {
        Document document = Jsoup.parse(pageSource);
        String mainImageUrl = extractMainImageUrl(document);
        String operatingTime = generateOperatingTime(extractPerformanceDuration(document),
                extractPerformanceSchedule(document));
        String[] period = extractTextByClass(document, "ps-date")
                .replace(".", "-")
                .split(PERFORMANCE_PERIOD_DELIMETER);
        LocalDate startDate = LocalDate.parse(period[0], DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(period[1], DateTimeFormatter.ISO_DATE);
        String extractedPrice = extractTextByClass(document, "rn-product-price1")
                .replace("원 ", "원\n");

        return CrawledItemDto.builder()
                .exhibitionType(ExhibitionTypeConverter.of(extractExhibitionType(document)))
                .exposureType(ExposureType.findExposureType(startDate, endDate).name())
                .startDate(startDate)
                .endDate(endDate)
                .feeType(Yes24FeeTypeConverter.convertFeeType(extractedPrice))
                .price(extractedPrice)
                .ticketUrl(pageUrl)
                .regionType(RegionTypeConverter.of(extractRegion(document)))
                .listImageUrl(mainImageUrl)
                .thumbnailImageUrl(mainImageUrl)
                .operatingTime(operatingTime)
                .itemDetailImages(extractIntroductionImages(document))
                .placeName(extractTextByClass(document, "ps-location"))
                .title(extractTextByClass(document, "rn-big-title"))
                .build();
    }

    private String extractTextByClass(final Document document, final String className) {
        return document.getElementsByClass(className)
                .text();
    }

    private String extractExhibitionType(final Document document) {
        return document.getElementsByClass("rn-location")
                .get(0)
                .child(0)
                .text();
    }

    private String extractMainImageUrl(final Document document) {
        return document.getElementsByClass("rn-product-imgbox")
                .get(0)
                .getElementsByTag("img")
                .get(0)
                .attr("src");
    }

    private String extractPerformanceDuration(final Document document) {
        String performanceDuration = document.getElementsByClass("rn-03-right")
                .get(0)
                .getElementsByTag("dl")
                .get(0)
                .child(3)
                .text();
        if (performanceDuration.equals("-")) {
            return "";
        }
        return performanceDuration;
    }

    private String extractPerformanceSchedule(final Document document) {
        return document.getElementsByClass("rn-product-area3")
                .get(0)
                .getElementsByTag("dl")
                .get(0)
                .child(1)
                .text()
                .replace("*", "\n*");
    }

    private String generateOperatingTime(final String performanceDuration, final String performanceSchedule) {
        if (performanceSchedule.equals(NO_PERFORMANCE_DURATION)) {
            return performanceSchedule;
        }
        return performanceDuration.substring(PERFORMANCE_SCHEDULE_INCLUSIVE_MIN_LENGTH,
                PERFORMANCE_SCHEDULE_EXCLUSIVE_MAX_LENGTH);
    }

    private List<String> extractIntroductionImages(final Document document) {
        return Objects.requireNonNull(document.getElementById("divPerfContent"))
                .childNodes()
                .stream()
                .map(Node::firstChild)
                .filter(Objects::nonNull)
                .map(node -> node.attr("src"))
                .collect(Collectors.toList());
    }

    private String extractRegion(final Document document) {
        return Objects.requireNonNull(document.getElementById("TheaterAddress"))
                .text()
                .split(" ")[0];
    }
}
