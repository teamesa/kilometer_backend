package com.esa.backend.batch.jobs.hello;

import com.esa.backend.batch.domain.hello.OtherHelloWorldTasklet;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OtherHelloBatchConfig {
    private static final String JOB_NAME = "backend.batch.other.hello.job";
    private static final String STEP_NAME = "backend.batch.other.hello.job";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final OtherHelloWorldTasklet otherHelloWorldTasklet;

    @Bean
    public Job otherHelloWorldJob(Step otherHelloWorldStep) {
        return jobBuilderFactory.get(JOB_NAME)
                .flow(otherHelloWorldStep)
                .end()
                .build();
    }

    @Bean
    public Step otherHelloWorldStep() {
        return stepBuilderFactory.get(STEP_NAME)
                .tasklet(otherHelloWorldTasklet)
                .build();
    }
}