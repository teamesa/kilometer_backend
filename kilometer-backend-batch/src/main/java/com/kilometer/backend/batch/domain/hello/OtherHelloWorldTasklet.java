package com.kilometer.backend.batch.domain.hello;

import com.kilometer.domain.hello.HelloResponse;
import com.kilometer.domain.hello.HelloService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OtherHelloWorldTasklet implements Tasklet {
    private final HelloService helloService;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        log.info("Hello, World! this is other job!");
        HelloResponse helloResponse = helloService.runInBatch("other lalala");
        log.info("Hello Response: {}", helloResponse);
        return RepeatStatus.FINISHED;
    }
}