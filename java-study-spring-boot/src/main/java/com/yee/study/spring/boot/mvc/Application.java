package com.yee.study.spring.boot.mvc;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 应用入口程序
 *
 * 使用 @SpringBootApplication 等于启用了如下3个注解
 *  1: @Configuration
 *  2: @EnableAutoConfiguration
 *  3: @ComponentScan
 * @author Roger.Yi
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.yee.study.spring.boot.mvc")
public class Application
{
    public static void main(String[] args)
    {
//        SpringApplication.run(Application.class, args);

        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
