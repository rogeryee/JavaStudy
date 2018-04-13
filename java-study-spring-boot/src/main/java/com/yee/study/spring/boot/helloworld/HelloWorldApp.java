package com.yee.study.spring.boot.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger.Yi
 */
@RestController
@EnableAutoConfiguration
public class HelloWorldApp
{
    @RequestMapping("/")
    String home()
    {
        return "Hello World!";
    }

    public static void main(String[] args) throws Exception
    {
        // http://localhost:8080/
        SpringApplication.run(HelloWorldApp.class, args);
    }
}
