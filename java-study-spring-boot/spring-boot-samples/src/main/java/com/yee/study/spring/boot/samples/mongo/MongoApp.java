package com.yee.study.spring.boot.samples.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.InputStream;
import java.util.Properties;

/**
 * Mongodb示例入口程序
 *
 * @author Roger.Yi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MongoApp {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = MongoApp.class.getClassLoader().getResourceAsStream("mongo/mongo.properties");
        properties.load(inputStream);

        SpringApplication application = new SpringApplication(MongoApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
