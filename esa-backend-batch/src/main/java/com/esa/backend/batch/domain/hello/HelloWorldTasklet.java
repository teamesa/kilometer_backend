package com.esa.backend.batch.domain.hello;

import com.esa.domain.hello.HelloResponse;
import com.esa.domain.hello.HelloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class HelloWorldTasklet implements Tasklet {
    private final HelloService helloService;
    @Value("#{jobParameters[memo]}")
    public String memo = "default";

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        log.info("Hello, World!");
        HelloResponse helloResponse = helloService.runInBatch(memo);
        log.info("Hello Response: {}", helloResponse);
        return RepeatStatus.FINISHED;
    }
}