package com.yee.study.spring.cloud.feign.provider;

import com.yee.study.spring.cloud.util.PropertiesUtil;
import com.yee.study.util.ArrayUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.text.MessageFormat;
import java.util.Properties;

/**
 * 应用服务提供者（将服务注册到相应的Eureka Server）
 * 
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableEurekaClient
public class ServiceProviderApp {

    private static final String MESSAGE_PATH = "feign/service-provider.properties";

    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesUtil.loadProperties(MESSAGE_PATH);
        SpringApplication application = new SpringApplication(ServiceProviderApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
