package com.esa.backend.batch.jobs.hello;

import com.esa.backend.batch.domain.hello.HelloWorldTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloBatchConfig {
    private static final String JOB_NAME = "backend.batch.hello.job";
    private static final String STEP_NAME = "backend.batch.hello.step";

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private HelloWorldTasklet helloWorldTasklet;

    @Bean
    public Job helloWorldJob(Step helloWorldStep) {
        return jobBuilderFactory.get(JOB_NAME)
                .flow(helloWorldStep)
                .end()
                .build();
    }

    @Bean
    public Step helloWorldStep() {
        return stepBuilderFactory.get(STEP_NAME)
                .tasklet(helloWorldTasklet)
                .build();
    }
}