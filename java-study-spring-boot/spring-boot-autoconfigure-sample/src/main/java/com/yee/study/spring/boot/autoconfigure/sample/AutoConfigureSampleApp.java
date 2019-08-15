package com.yee.study.spring.boot.autoconfigure.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Roger.Yi
 */
@SpringBootApplication
public class AutoConfigureSampleApp {
    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(AutoConfigureSampleApp.class);
        application.run(args);
    }
}
