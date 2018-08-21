package com.yee.study.spring.cloud.zuul.gateway;

import com.yee.study.spring.cloud.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import java.util.Properties;

/**
 * 应用服务消费者
 * 
 * @author Roger.Yi
 */
@SpringBootApplication
@EnableZuulProxy
public class GatewayApp {
    public static void main(String[] args) throws Exception {

//        String props = "zuul/gateway.properties";
        String props = "zuul/gateway-routes.properties";  // 增加了路由的配置
        
        Properties properties = PropertiesUtil.loadProperties(props);
        SpringApplication application = new SpringApplication(GatewayApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
