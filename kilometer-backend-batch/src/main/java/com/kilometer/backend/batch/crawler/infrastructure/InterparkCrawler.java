package com.kilometer.backend.batch.crawler.infrastructure;

import com.kilometer.backend.batch.crawler.domain.Crawler;
import com.kilometer.domain.crawledItem.dto.CrawledItemDto;
import com.kilometer.backend.batch.crawler.util.ExhibitionTypeConverter;
import com.kilometer.backend.batch.crawler.util.RegionTypeConverter;
import com.kilometer.domain.item.enumType.ExposureType;
import com.kilometer.domain.item.enumType.FeeType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class InterparkCrawler implements Crawler {

    private static final String PERFORMANCE_PERIOD_DELIMITER = "~";
    private static final String HTTPS_URL_PREFIX = "https://";
    private static final int PERFORMANCE_SCHEDULE_INCLUSIVE_MIN_LENGTH = 0;
    private static final int PERFORMANCE_SCHEDULE_EXCLUSIVE_MAX_LENGTH = 101;

    @Value("${crawler.target.interpark.origin}")
    public String ORIGIN;

    @Value("#{'${crawler.target.interpark.category}'.split(',')}")
    private List<String> PERFORMANCE_CATEGORY_URLS;

    @Value("${crawler.selenium.remote-driver-url}")
    private String remoteDriverUrl;

    @Override
    public List<CrawledItemDto> generateItem() {
        return PERFORMANCE_CATEGORY_URLS.parallelStream()
                .map(this::extractPerformanceDetailPagesUrls)
                .flatMap(Collection::stream)
                .map(this::extractItemDetail)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
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

    private Optional<CrawledItemDto> extractItemDetail(final String pageUrl) {
        ChromeDriver<CrawledItemDto> chromeDriver = new ChromeDriver<>(remoteDriverUrl);
        Function<WebDriver, ExpectedCondition<?>> renderingCondition = (driver) -> {
            try {
                driver.findElement(By.id("container"));
                return ExpectedConditions.presenceOfElementLocated(By.id("popup-info-place"));
            } catch (NoSuchElementException e) {
                return ExpectedConditions.presenceOfElementLocated(By.id("gateway_warp"));
            }
        };
        List<Function<WebDriver, ExpectedCondition<?>>> expectedRenderingConditions = List.of(renderingCondition);
        Function<String, CrawledItemDto> page = pageSource -> parsePage(pageSource, pageUrl);
        return chromeDriver.crawlUrl(pageUrl, page, expectedRenderingConditions);
    }

    private CrawledItemDto parsePage(final String pageSource, final String pageUrl) {
        Document document = Jsoup.parse(pageSource);
        String[] period = extractPeriod(document);
        LocalDate startDate = LocalDate.parse(period[0], DateTimeFormatter.ISO_DATE);
        LocalDate endDate = LocalDate.parse(period[period.length - 1], DateTimeFormatter.ISO_DATE);
        String mainImageUrl = extractMainImageUrl(document);

        return CrawledItemDto.builder()
                .exhibitionType(ExhibitionTypeConverter.of(extractExhibitionType(document)))
                .exposureType(ExposureType.findExposureType(startDate, endDate).name())
                .startDate(startDate)
                .endDate(endDate)
                .feeType(FeeType.COST.name())
                .price(extractPrice(document))
                .ticketUrl(pageUrl)
                .regionType(RegionTypeConverter.of(extractRegion(document)))
                .listImageUrl(mainImageUrl)
                .thumbnailImageUrl(mainImageUrl)
                .operatingTime(generateOperatingTime(document))
                .itemDetailImages(extractIntroductionImages(document))
                .placeName(extractPlace(document))
                .title(extractTextByClassName(document, "prdTitle", 0))
                .build();
    }

    private String[] extractPeriod(final Document document) {
        return extractTextByClassName(document, "infoText", 0)
                .replace(" ", "")
                .replace(".", "-")
                .split(PERFORMANCE_PERIOD_DELIMITER);
    }

    private String extractTextByClassName(final Document document, final String className, final int elementSequence) {
        return document.getElementsByClass(className)
                .get(elementSequence)
                .text();
    }

    private String extractTextByClassName(final Element element, final String className, final int elementSequence) {
        return element.getElementsByClass(className)
                .get(elementSequence)
                .text();
    }

    private String extractExhibitionType(final Document document) {
        return extractTextByClassName(document, "tagText", 0).split(" ")[0];
    }

    private String extractPrice(final Document document) {
        String rawPrice = document.getElementsByClass("infoPriceItem")
                .stream()
                .map(Element::text)
                .collect(Collectors.joining());

        return rawPrice.substring(rawPrice.indexOf(")") + 1)
                .replace("원", "원\n")
                .replace("석", "석: ");
    }

    private String extractRegion(final Document document) {
        return document.getElementsByClass("popPlaceInfo")
                .stream()
                .map(Element::children)
                .flatMap(Collection::stream)
                .filter(element -> element.text().contains("주소"))
                .map(element -> element.getElementsByTag("span").text().split(" ")[0])
                .findFirst()
                .orElse("");
    }

    private String extractMainImageUrl(final Document document) {
        return HTTPS_URL_PREFIX + document.getElementsByClass("posterBoxImage")
                .get(0)
                .attr("src")
                .substring(2);
    }

    private String generateOperatingTime(final Document document) {
        String operatingTime = extractPerformanceDuration(document)
                .orElseGet(() -> extractTextByClassName(document, "contentDetailList", 0));

        return operatingTime.substring(PERFORMANCE_SCHEDULE_INCLUSIVE_MIN_LENGTH,
                Math.min(operatingTime.length(), PERFORMANCE_SCHEDULE_EXCLUSIVE_MAX_LENGTH));
    }

    private Optional<String> extractPerformanceDuration(final Document document) {
        return document.getElementsByClass("infoItem")
                .stream()
                .filter(element -> extractTextByClassName(element, "infoLabel", 0)
                        .contains("공연시간"))
                .map(element -> extractTextByClassName(element, "infoText", 0))
                .findFirst();
    }

    private List<String> extractIntroductionImages(final Document document) {
        return document.getElementsByClass("contentDetail")
                .stream()
                .filter(element -> element.getElementsByTag("title").size() > 0)
                .map(element -> HTTPS_URL_PREFIX + element.getElementsByTag("img")
                        .attr("src")
                        .substring(2))
                .collect(Collectors.toList());
    }

    private String extractPlace(final Document document) {
        String rawPlaceName = extractTextByClassName(document, "infoBtn", 0);
        return rawPlaceName.substring(0, rawPlaceName.indexOf("("));
    }
}
