package com.yee.study.spring.cloud.eureka.client;

import com.yee.study.spring.cloud.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Properties;

/**
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableEurekaClient
public class EurekaClientApp {

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesUtil.loadProperties("eureka/eureka-client.properties");
        SpringApplication application = new SpringApplication(EurekaClientApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
