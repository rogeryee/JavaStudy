package com.yee.study.spring.framework.ioc.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.Ordered;

import java.beans.PropertyDescriptor;

/**
 * 定制化的 InstantiationAwareBeanPostProcessor
 *
 * @author Roger.Yi
 */
@Slf4j
public class MyInstantiationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, Ordered {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        log.info("MyInstantiationBeanPostProcessor.BeforeInstantiation beanName={}, beanClass={}", beanName, beanClass);
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        log.info("MyInstantiationBeanPostProcessor.postProcessAfterInstantiation beanName={}, bean={}", beanName, bean);
        return true;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        log.info("MyInstantiationBeanPostProcessor.postProcessPropertyValues beanName={}, bean={}", beanName, bean);
        return pvs;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
