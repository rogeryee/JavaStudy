package com.yee.study.spring.framework.ioc.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * 定制化的 InstantiationAwareBeanPostProcessor
 *
 * @author Roger.Yi
 */
public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanClass == MessageServiceImpl.class) {
            return new MessageService() {
                @Override
                public String getMessage() {
                    return "hello world from MyInstantiationAwareBeanPostProcessor";
                }
            };
        }
        return null;
    }
}
