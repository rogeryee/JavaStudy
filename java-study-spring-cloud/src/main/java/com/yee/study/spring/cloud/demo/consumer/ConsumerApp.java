package com.yee.study.spring.cloud.demo.consumer;

import com.yee.study.spring.cloud.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Properties;

/**
 * 服务消费者（Consumer）引用入口
 * @author Roger.Yi
 */
@SpringBootApplication
public class ConsumerApp {
    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesUtil.loadProperties("demo/consumer.properties");
        SpringApplication application = new SpringApplication(ConsumerApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
