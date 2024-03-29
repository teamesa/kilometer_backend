package com.kilometer.backend.batch.domain.performancepage;

import com.kilometer.backend.batch.crawler.domain.Crawler;
import com.kilometer.domain.crawledItem.dto.CrawledItemDto;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PageCrawlingTasklet implements Tasklet {

    private final Crawler yes24Crawler;
    private final Crawler interparkCrawler;

    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext) throws Exception {
        List<CrawledItemDto> crawledItemDtos = crawlPages();
        Collections.shuffle(crawledItemDtos);

        chunkContext.getStepContext()
                .getStepExecution()
                .getJobExecution()
                .getExecutionContext()
                .put("performancePages", limitCrawledPages(crawledItemDtos, 40));
        return RepeatStatus.FINISHED;
    }

    private List<CrawledItemDto> crawlPages() {
        return Stream.concat(yes24Crawler.generateItem().stream(),
                        interparkCrawler.generateItem().stream())
                .collect(Collectors.toList());
    }

    private List<CrawledItemDto> limitCrawledPages(final List<CrawledItemDto> crawledItemDtos, final int maxSize) {
        return crawledItemDtos.stream()
                .limit(maxSize)
                .collect(Collectors.toList());
    }
}
