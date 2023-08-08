package com.kilometer.backend.batch.alert.infrastructure;

import com.kilometer.backend.batch.alert.domain.MessageSender;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SlackMessageSender implements MessageSender {

    @Value("${message.slack.webHookUrl}")
    private String webHookUrl;

    @Override
    public void sendMessage(final String message) {
        new RestTemplate().exchange(webHookUrl, HttpMethod.POST,
                new HttpEntity<>(Map.of("text", message)), Void.class);
    }
}
