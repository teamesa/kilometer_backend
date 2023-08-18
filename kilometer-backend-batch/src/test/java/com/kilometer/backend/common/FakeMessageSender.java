package com.kilometer.backend.common;

import com.kilometer.backend.batch.alert.domain.MessageSender;

public class FakeMessageSender implements MessageSender {

    @Override
    public void sendMessage(final String message) {
        System.out.println("send message: " + message);
    }
}
