package com.yee.study.spring.boot.jdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import java.io.InputStream;
import java.util.Properties;

/**
 * JDBC示例入口程序
 *
 * @author Roger.Yi
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class JdbcApp {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = JdbcApp.class.getClassLoader().getResourceAsStream("jdbc/jdbc.properties");
        properties.load(inputStream);

        SpringApplication application = new SpringApplication(JdbcApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
