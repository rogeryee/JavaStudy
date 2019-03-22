package com.yee.study.spring.cloud.feign.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 应用服务消费者
 *
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableFeignClients
public class ServiceConsumerApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ServiceConsumerApplication.class);
        application.run(args);
    }
}
