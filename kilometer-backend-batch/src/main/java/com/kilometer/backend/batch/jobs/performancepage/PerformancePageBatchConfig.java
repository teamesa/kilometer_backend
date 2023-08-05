package com.kilometer.backend.batch.jobs.performancepage;

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

    @Bean
    public Job crawlPerformances() {
        return jobBuilderFactory.get("generate performance")
                .start(crawPerformancePages())
                .next(outputPerformances())
                .build();
    }


    @Bean
    public Step crawPerformancePages() {
        return stepBuilderFactory.get("performance page crawler")
                .tasklet(pageCrawlingTasklet)
                .build();
    }


    @Bean
    public Step outputPerformances() {
        return stepBuilderFactory.get("output performances")
                .tasklet(performanceOutputTasklet)
                .build();
    }
}
