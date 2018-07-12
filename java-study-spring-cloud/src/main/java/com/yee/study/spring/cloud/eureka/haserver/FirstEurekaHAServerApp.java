package com.yee.study.spring.cloud.eureka.haserver;

import com.yee.study.spring.cloud.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

import java.util.Properties;

/**
 * Eureka Server 1 - HA (集群示例)
 *
 * 会和{@link SecondEurekaHAServerApp}组成一个集群，保证高可用
 *
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableEurekaServer
public class FirstEurekaHAServerApp {

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesUtil.loadProperties("eureka/eureka-server-ha-first.properties");
        SpringApplication application = new SpringApplication(FirstEurekaHAServerApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
