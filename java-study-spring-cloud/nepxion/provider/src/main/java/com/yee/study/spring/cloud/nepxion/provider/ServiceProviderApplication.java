package com.yee.study.spring.cloud.nepxion.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

/**
 * 应用服务提供者（将服务注册到相应的Eureka Server）
 *
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ServiceProviderApplication.class);
        application.run(args);
    }

    @Bean
    public DiscoveryGrayEnabledStrategy zuulEnabledStrategy() {
        return new DiscoveryGrayEnabledStrategy();
    }
}
