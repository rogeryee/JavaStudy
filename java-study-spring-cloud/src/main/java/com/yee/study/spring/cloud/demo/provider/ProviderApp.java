package com.yee.study.spring.cloud.demo.provider;

import com.yee.study.spring.cloud.util.PropertiesUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.InputStream;
import java.util.Properties;

/**
 * 示例中服务提供者（Provider）引用入口
 * @author Roger.Yi
 */
@SpringBootApplication
public class ProviderApp {
    public static void main(String[] args) throws Exception {
        Properties properties = PropertiesUtil.loadProperties("demo/provider.properties");
        SpringApplication application = new SpringApplication(ProviderApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
