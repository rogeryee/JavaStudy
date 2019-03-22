package com.yee.study.spring.cloud.feign.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 应用服务提供者（将服务注册到相应的Eureka Server）
 *
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceProviderApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(ServiceProviderApplication.class);
        application.run(args);
    }
}
