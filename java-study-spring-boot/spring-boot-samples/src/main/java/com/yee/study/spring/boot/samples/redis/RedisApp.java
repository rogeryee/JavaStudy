package com.yee.study.spring.boot.samples.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import java.io.InputStream;
import java.util.Properties;

/**
 * Redis示例入口程序
 *
 * @author Roger.Yi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class RedisApp {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = RedisApp.class.getClassLoader().getResourceAsStream("redis/redis.properties");
        properties.load(inputStream);

        SpringApplication application = new SpringApplication(RedisApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
