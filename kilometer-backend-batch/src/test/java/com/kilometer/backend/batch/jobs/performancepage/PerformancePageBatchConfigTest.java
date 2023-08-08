package com.kilometer.backend.batch.jobs.performancepage;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.kilometer.backend.batch.alert.infrastructure.SlackMessageSender;
import com.kilometer.backend.batch.domain.performancepage.FinishMessageTasklet;
import com.kilometer.backend.batch.domain.performancepage.PageCrawlingTasklet;
import com.kilometer.backend.batch.domain.performancepage.PerformanceOutputTasklet;
import com.kilometer.backend.common.BatchTestConfig;
import com.kilometer.backend.common.FakeCrawler;
import com.kilometer.backend.common.FakeMessageSender;
import com.kilometer.backend.common.Fixture;
import com.kilometer.domain.crawledItem.CrawledItem;
import com.kilometer.domain.crawledItem.CrawledItemRepository;
import com.kilometer.domain.crawledItem.CrawledItemService;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

@DisplayName("PerformancePageBatchConfig 는 ")
@SpringBatchTest
@SpringBootTest(classes = {BatchTestConfig.class, PerformancePageBatchConfig.class,
        PageCrawlingTasklet.class, PerformanceOutputTasklet.class, FakeCrawler.class,
        CrawledItemService.class, CrawledItemRepository.class, Fixture.class,
        FakeMessageSender.class, FinishMessageTasklet.class})
class PerformancePageBatchConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @Autowired
    private CrawledItemRepository crawledItemRepository;

    @DisplayName("공연 정보를 크롤링하고 저장해야 한다.")
    @Test
    void crawlingPerformanceJob() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        (new TransactionTemplate(platformTransactionManager)).execute((status) -> {
            CrawledItem actual = crawledItemRepository.getById(1L);
            assertAll(
                    () -> assertThat(actual.getId()).isEqualTo(1L),
                    () -> assertThat(actual.getCrawledItemDetailImages().size()).isEqualTo(5),
                    () -> assertThat(jobExecution.getExitStatus()).isEqualTo(ExitStatus.COMPLETED)
            );
            return null;
        });
    }

    @DisplayName("중복된 공연 정보는 저장하지 말아야 한다.")
    @Test
    void ignoreDuplicatedCralwedItem() throws Exception {
        jobLauncherTestUtils.launchJob();
        jobLauncherTestUtils.launchJob();

        List<CrawledItem> actual = crawledItemRepository.findAll();

        assertThat(actual.size()).isEqualTo(1);
    }
}
