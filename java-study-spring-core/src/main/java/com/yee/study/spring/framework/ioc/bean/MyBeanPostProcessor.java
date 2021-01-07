package com.yee.study.spring.framework.ioc.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;

/**
 * 定制的BeanPostProcessor1
 *
 * @author Roger.Yi
 */
@Slf4j
public class MyBeanPostProcessor implements BeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("MyBeanPostProcessor.BeforeInitialization beanName={} bean={}", beanName, bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.info("MyBeanPostProcessor.AfterInitialization beanName={} bean={}", beanName, bean);
        return bean;
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
