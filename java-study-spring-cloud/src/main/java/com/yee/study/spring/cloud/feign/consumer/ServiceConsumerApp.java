package com.yee.study.spring.cloud.feign.consumer;

import com.yee.study.spring.cloud.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.Properties;

/**
 * 应用服务消费者
 *
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableFeignClients
public class ServiceConsumerApp {
    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesUtil.loadProperties("feign/service-consumer.properties");
        SpringApplication application = new SpringApplication(ServiceConsumerApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
