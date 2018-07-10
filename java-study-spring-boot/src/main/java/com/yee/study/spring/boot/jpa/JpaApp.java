package com.yee.study.spring.boot.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import java.io.InputStream;
import java.util.Properties;

/**
 * JPA示例入口程序
 *
 * @author Roger.Yi
 */
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class JpaApp {
    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        InputStream inputStream = JpaApp.class.getClassLoader().getResourceAsStream("jpa/jpa.properties");
        properties.load(inputStream);

        SpringApplication application = new SpringApplication(JpaApp.class);
        application.setDefaultProperties(properties);
        application.run(args);
    }
}
