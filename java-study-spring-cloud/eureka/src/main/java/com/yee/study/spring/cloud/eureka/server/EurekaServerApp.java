package com.yee.study.spring.cloud.eureka.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 单节点的Eureka Server示例
 *
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApp {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EurekaServerApp.class);
        application.run(args);
    }
}
