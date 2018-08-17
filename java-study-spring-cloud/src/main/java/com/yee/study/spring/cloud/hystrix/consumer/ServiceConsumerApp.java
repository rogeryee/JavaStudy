package com.yee.study.spring.cloud.hystrix.consumer;

import com.yee.study.spring.cloud.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

import java.util.Properties;

/**
 * 应用服务消费者
 * 
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableHystrix
public class ServiceConsumerApp {
    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesUtil.loadProperties("hystrix/service-consumer.properties");
        SpringApplication application = new SpringApplication(ServiceConsumerApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
