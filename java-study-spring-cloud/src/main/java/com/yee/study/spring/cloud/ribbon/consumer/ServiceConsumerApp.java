package com.yee.study.spring.cloud.ribbon.consumer;

import com.yee.study.spring.cloud.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

/**
 * 应用服务消费者
 * 
 * @author Roger.Yi
 */
@SpringBootApplication
public class ServiceConsumerApp {
    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesUtil.loadProperties("ribbon/service-consumer.properties");
        SpringApplication application = new SpringApplication(ServiceConsumerApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
