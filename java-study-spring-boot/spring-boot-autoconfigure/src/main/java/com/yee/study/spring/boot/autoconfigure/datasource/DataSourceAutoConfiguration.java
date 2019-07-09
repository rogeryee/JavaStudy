package com.yee.study.spring.boot.autoconfigure.datasource;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
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
public class DataSourceAutoConfiguration implements BeanFactoryAware, ApplicationContextAware, InitializingBean {

    private BeanFactory beanFactory;

    private ApplicationContext context;

    @Bean
    @ConfigurationProperties(prefix = "ds")
    public DataSourceHolderProperties dataSourceHolderProperties() {

        log.info("DataSourceHolderProperties created.");
        return new DataSourceHolderProperties();
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
        log.info("DataSourceAutoConfiguration afterProperties");

        DataSourceModel ds1 = new DataSourceModel();
        ds1.setId("ds-1");
        ds1.setName("ds-name-1");

        DataSourceModel ds2 = new DataSourceModel();
        ds2.setId("ds-2");
        ds2.setName("ds-name-2");

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(DataSourceModel.class);
        builder.addPropertyValue("id", "ds-1");
        builder.addPropertyValue("name", "ds-name-1");
        BeanDefinition beanDefinition = builder.getBeanDefinition();

        DefaultListableBeanFactory factory = (DefaultListableBeanFactory) this.beanFactory;
        factory.registerBeanDefinition(ds1.getId(), beanDefinition);

        BeanDefinitionBuilder.genericBeanDefinition(DataSourceModel.class);
        builder.addPropertyValue("id", "ds-2");
        builder.addPropertyValue("name", "ds-name-2");
        beanDefinition = builder.getBeanDefinition();
        factory.registerBeanDefinition(ds2.getId(), beanDefinition);
    }

    @Data
    public static class DataSourceHolderProperties {

        private List<DataSourceProperties> dataSources;

        @Data
        public static class DataSourceProperties {

            private String id;

            private String name;
        }
    }
}
