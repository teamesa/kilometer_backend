package com.kilometer.backend.batch.crawler.infrastructure;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ChromeDriver<T> {

    private static final int WEB_DRIVER_WAIT_TIME_OUT = 5;

    private final String remoteDriverUrl;

    public ChromeDriver(final String remoteDriverUrl) {
        this.remoteDriverUrl = remoteDriverUrl;
    }

    public Optional<T> crawlUrl(final String targetUrl, final Function<String, T> parseResult,
                                final List<Function<WebDriver, ExpectedCondition<?>>> waitingConditions) {
        WebDriver chromeDriver = null;
        try {
            chromeDriver = new RemoteWebDriver(new URL(remoteDriverUrl), makeChromeOptions());
            WebDriverWait webDriverWait = new WebDriverWait(chromeDriver, WEB_DRIVER_WAIT_TIME_OUT);
            chromeDriver.get(targetUrl);
            waitingConditions.forEach(webDriverWait::until);
            return Optional.of(parseResult.apply(chromeDriver.getPageSource()));
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        } catch (Exception e) {
            return Optional.empty();
        } finally {
            assert chromeDriver != null;
            chromeDriver.quit();
        }
    }

    private ChromeOptions makeChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless", "--disable-extensions", "--block-new-web-contents");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return chromeOptions;
    }
}
