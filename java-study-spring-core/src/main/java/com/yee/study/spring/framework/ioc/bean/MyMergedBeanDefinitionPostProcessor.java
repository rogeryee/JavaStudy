package com.yee.study.spring.framework.ioc.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.Ordered;

/**
 * @author Roger.Yi
 */
@Slf4j
public class MyMergedBeanDefinitionPostProcessor implements MergedBeanDefinitionPostProcessor, Ordered {

    @Override
    public void postProcessMergedBeanDefinition(RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        log.info("MyMergedBeanDefinitionPostProcessor.MergedBeanDefinition beanName={}", beanName);
    }

    @Override
    public int getOrder() {
        return 4;
    }
}
