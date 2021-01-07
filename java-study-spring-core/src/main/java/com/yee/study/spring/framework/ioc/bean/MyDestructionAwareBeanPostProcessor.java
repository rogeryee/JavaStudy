package com.yee.study.spring.framework.ioc.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.core.Ordered;

/**
 * 定制的 DestructionAwareBeanPostProcessor
 * @author Roger.Yi
 */
@Slf4j
public class MyDestructionAwareBeanPostProcessor implements DestructionAwareBeanPostProcessor, Ordered {

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        log.info("MyDestructionAwareBeanPostProcessor.BeforeDestruction beanName={} bean={}", beanName, bean);
    }

    @Override
    public boolean requiresDestruction(Object bean) {
        return true;
    }

    @Override
    public int getOrder() {
        return 3;
    }
}
