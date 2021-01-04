package com.yee.study.spring.boot.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.io.InputStream;
import java.util.Properties;

/**
 * HelloWorld启动器
 * <p>
 * 1. @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
 * 2. @ComponentScan 如果不设置basePackage的话 默认会扫描包的所有类，所以最好还是写上basePackage ,减少加载时间。
 * 3. @Configuration 表示这个类是一个spring 配置类，一般这里面会定义Bean，会把这个类中bean加载到spring容器中
 * 4. @EnableAutoConfiguration springboot的注解 会在你开启某些功能的时候自动配置
 * <p>
 * 本例中，应为在pom.xml中增加了对数据源的依赖，所以需要在加载是排除以下配置，否者会报数据源配置出错
 * DataSourceAutoConfiguration.class, MongoAutoConfiguration.class, MongoDataAutoConfiguration.class
 *
 * @author Roger.Yi
 */
@SpringBootApplication
public class HelloWorldApp {
    public static void main(String[] args) throws Exception {
        SpringApplication application = new SpringApplication(HelloWorldApp.class);
        application.run(args);
    }
}
