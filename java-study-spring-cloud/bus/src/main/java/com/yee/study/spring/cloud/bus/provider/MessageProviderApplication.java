package com.yee.study.spring.cloud.bus.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 消息服务提供者（将服务注册到相应的Eureka Server）
 *
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableEurekaClient
public class MessageProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MessageProviderApplication.class);
        application.run(args);
    }
}
