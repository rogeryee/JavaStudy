package com.yee.study.spring.cloud.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * Eureka Client示例
 * <p>
 * 将服务注册到相应的Eureka Server
 *
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableEurekaClient
public class EurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EurekaClientApplication.class);
        application.run(args);
    }
}
