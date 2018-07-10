package com.yee.study.spring.boot.yaml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

import java.io.InputStream;
import java.util.Properties;

/**
 * 加载自定义Yaml文件示例
 *
 * @author Roger.Yi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class CustomYamlApp {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(CustomYamlApp.class, args);
    }
}
