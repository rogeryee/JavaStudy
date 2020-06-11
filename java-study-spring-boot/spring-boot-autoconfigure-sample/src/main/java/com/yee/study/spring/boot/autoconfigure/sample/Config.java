package com.yee.study.spring.boot.autoconfigure.sample;

import com.yee.study.spring.boot.autoconfigure.datasource.DataSourceModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Roger.Yi
 */
@Configuration
public class Config {

    @Bean("testBean1")
    public TestBean testBean() {
        System.out.println("######## Sample.Bean: TestBean created.");
        return new TestBean();
    }

//    @Bean("testBean2")
//    public TestBean testBean2(@Qualifier("ds-1") DataSourceModel dataSourceModel) {
//        System.out.println("######## Sample.Bean: TestBean2 created, ds = " + dataSourceModel);
//        return new TestBean();
//    }

    public static class TestBean {

    }
}
