package com.yee.study.spring.cloud.bus.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * 消息消费者（将服务注册到相应的Eureka Server）
 *
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableEurekaClient
public class MessageConsumerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MessageConsumerApplication.class);
        application.run(args);
    }
}
