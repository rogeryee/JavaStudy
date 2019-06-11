package com.yee.study.spring.cloud.nepxion.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

/**
 * Zuul Server示例
 *
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
public class ZuulApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ZuulApplication.class);
        application.run(args);
    }

    @Bean
    public DiscoveryGrayEnabledStrategy zuulEnabledStrategy() {
        return new DiscoveryGrayEnabledStrategy();
    }
}
