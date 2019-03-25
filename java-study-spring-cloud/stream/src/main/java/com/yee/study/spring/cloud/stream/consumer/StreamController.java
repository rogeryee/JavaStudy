package com.yee.study.spring.cloud.stream.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger.Yi
 */
@RestController
public class StreamController {

    @Autowired
    private StreamClient streamClient;

    @GetMapping("send")
    public void send() {
        streamClient.output().send(MessageBuilder.withPayload("Hello World...").build());
    }
}
