package com.yee.study.spring.boot.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloWorld启动器
 *
 * 1. @SpringBootApplication = @Configuration + @EnableAutoConfiguration + @ComponentScan
 * 2. @ComponentScan 如果不设置basePackage的话 默认会扫描包的所有类，所以最好还是写上basePackage ,减少加载时间。
 * 3. @Configuration 表示这个类是一个spring 配置类，一般这里面会定义Bean，会把这个类中bean加载到spring容器中
 * 4. @EnableAutoConfiguration springboot的注解 会在你开启某些功能的时候自动配置
 * @author Roger.Yi
 */
@SpringBootApplication
public class HelloWorldApp
{
    public static void main(String[] args) throws Exception
    {
        SpringApplication.run(HelloWorldApp.class, args);
    }
}
