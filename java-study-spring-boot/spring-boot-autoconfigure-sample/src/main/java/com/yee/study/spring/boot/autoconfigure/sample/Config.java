package com.yee.study.spring.boot.autoconfigure.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Roger.Yi
 */
@Configuration
public class Config {

    @Bean
    public TestBean testBean() {
        System.out.println("######## Sample.Bean: TestBean created.");
        return new TestBean();
    }


    public static class TestBean {

    }
}
