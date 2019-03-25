package com.yee.study.spring.cloud.stream.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author Roger.Yi
 */
@Component
@EnableBinding(value = {StreamClient.class})
public class StreamReceiver {

    private Logger logger = LoggerFactory.getLogger(StreamReceiver.class);

    /**
     * 使用自定义的消息接收封装类 StreamClient.class
     *
     * @param message
     */
    @StreamListener(StreamClient.INPUT)
    public void receive(String message) {
        logger.info("StreamReceiver: {}", message);
    }
}
