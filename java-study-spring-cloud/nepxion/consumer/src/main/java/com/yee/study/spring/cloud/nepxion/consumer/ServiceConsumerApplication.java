package com.yee.study.spring.cloud.nepxion.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

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

    @Bean
    public DiscoveryGrayEnabledStrategy zuulEnabledStrategy() {
        return new DiscoveryGrayEnabledStrategy();
    }
}
