package com.yee.study.spring.cloud.bus.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 单节点的Eureka Server示例
 * <p>
 * 加载 application-eureka-server.yml
 * 环境变量设置: spring.profiles.active=eureka-server
 *
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EurekaServerApplication.class);
        application.run(args);
    }
}
