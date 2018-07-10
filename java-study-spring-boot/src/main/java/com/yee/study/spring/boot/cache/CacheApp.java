package com.yee.study.spring.boot.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

import java.io.InputStream;
import java.util.Properties;

/**
 * Cache示例入口程序
 *
 * @author Roger.Yi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@EnableCaching
public class CacheApp {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = CacheApp.class.getClassLoader().getResourceAsStream("cache/cache.properties");
        properties.load(inputStream);

        SpringApplication application = new SpringApplication(CacheApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
