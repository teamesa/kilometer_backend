package com.kilometer.backend.batch.domain.performancepage;

import com.kilometer.domain.crawledItem.CrawledItemService;
import com.kilometer.domain.crawledItem.dto.CrawledItemDto;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@StepScope
public class PerformanceOutputTasklet implements Tasklet {

    @Value("#{jobExecutionContext['performancePages']}")
    private List<CrawledItemDto> crawledItemDtos;
    private CrawledItemService crawledItemService;

    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext) throws Exception {
        crawledItemDtos.forEach(crawledItemService::saveCrawledItem);
        return RepeatStatus.FINISHED;
    }
}
