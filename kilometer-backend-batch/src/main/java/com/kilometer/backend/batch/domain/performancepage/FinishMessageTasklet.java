package com.kilometer.backend.batch.domain.performancepage;

import com.kilometer.backend.batch.alert.domain.MessageSender;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FinishMessageTasklet implements Tasklet {

    private static final String PERFORMANCE_CRAWLING_BATCH_DONE = "공연 정보 수집이 완료되었습니다.\n수집된 공연 정보 조회하기: ";

    private String adminPageUrl;
    private MessageSender slackMessageSender;

    public FinishMessageTasklet(@Value("${message.slack.adminPageUrl}") final String adminPageUrl, final MessageSender slackMessageSender) {
        this.adminPageUrl = adminPageUrl;
        this.slackMessageSender = slackMessageSender;
    }

    @Override
    public RepeatStatus execute(final StepContribution contribution, final ChunkContext chunkContext) throws Exception {
        slackMessageSender.sendMessage(PERFORMANCE_CRAWLING_BATCH_DONE + adminPageUrl);
        return RepeatStatus.FINISHED;
    }
}
