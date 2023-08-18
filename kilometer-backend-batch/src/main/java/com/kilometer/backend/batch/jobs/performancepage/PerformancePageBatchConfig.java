package com.kilometer.backend.batch.jobs.performancepage;

import com.kilometer.backend.batch.domain.performancepage.FinishMessageTasklet;
import com.kilometer.backend.batch.domain.performancepage.PageCrawlingTasklet;
import com.kilometer.backend.batch.domain.performancepage.PerformanceOutputTasklet;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class PerformancePageBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final PageCrawlingTasklet pageCrawlingTasklet;
    private final PerformanceOutputTasklet performanceOutputTasklet;
    private final FinishMessageTasklet finishMessageTasklet;

    @Bean
    public Job crawlPerformances() {
        return jobBuilderFactory.get("generatePerformance")
                .start(crawPerformancePages())
                .next(outputPerformances())
                .next(sendCompleteMessage())
                .build();
    }


    @Bean
    public Step crawPerformancePages() {
        return stepBuilderFactory.get("performancePageCrawler")
                .tasklet(pageCrawlingTasklet)
                .build();
    }


    @Bean
    public Step outputPerformances() {
        return stepBuilderFactory.get("outputPerformances")
                .tasklet(performanceOutputTasklet)
                .build();
    }

    @Bean
    public Step sendCompleteMessage() {
        return stepBuilderFactory.get("sendCompleteMessage")
                .tasklet(finishMessageTasklet)
                .build();
    }
}
