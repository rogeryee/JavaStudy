package com.yee.study.spring.cloud.ribbon.provider;

import com.yee.study.spring.cloud.util.PropertiesUtil;
import com.yee.study.util.ArrayUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

    private static final String MESSAGE_PATH = "ribbon/service-provider-{0}.properties";

    public static void main(String[] args) throws Exception {

        if(ArrayUtil.getElement(args, 0) == null) {
            throw new IllegalArgumentException("Application args is null.");
        }

        String path = MessageFormat.format(MESSAGE_PATH, args[0]);
        Properties properties = PropertiesUtil.loadProperties(path);
        SpringApplication application = new SpringApplication(ServiceProviderApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
