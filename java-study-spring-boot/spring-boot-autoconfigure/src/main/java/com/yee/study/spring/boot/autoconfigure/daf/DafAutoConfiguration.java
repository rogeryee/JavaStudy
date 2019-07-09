package com.yee.study.spring.boot.autoconfigure.daf;

import com.yee.study.spring.boot.autoconfigure.datasource.DataSourceAutoConfiguration;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Roger.Yi
 */
@Slf4j
@Configuration
@AutoConfigureAfter(value = {DataSourceAutoConfiguration.class})
public class DafAutoConfiguration implements BeanFactoryAware, ApplicationContextAware, InitializingBean {

    private BeanFactory beanFactory;

    private ApplicationContext context;

    @Bean
    @ConfigurationProperties(prefix = "daf")
    public DafTemplatesProperties dafTemplatesProperties() {
        log.info("DafTemplatesProperties created.");
        return new DafTemplatesProperties();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DafAutoConfiguration afterProperties");
    }

    @Data
    public static class DafTemplatesProperties {

        private List<TemplateProperties> templates;

        @Data
        public static class TemplateProperties {

            private String id;

            private String dsRef;
        }
    }
}
