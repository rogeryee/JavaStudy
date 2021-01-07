package com.yee.study.spring.framework.ioc.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;

/**
 * 定制的BeanFactoryPostProcessor1
 *
 * @author Roger.Yi
 */
@Slf4j
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("MyBeanFactoryPostProcessor.postProcessBeanFactory");
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
