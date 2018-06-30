package com.yee.study.spring.cloud.eureka;

import com.yee.study.spring.cloud.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.Properties;

/**
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaApp {

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesUtil.loadProperties("eureka/eureka-server.properties");
        SpringApplication application = new SpringApplication(EurekaApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
