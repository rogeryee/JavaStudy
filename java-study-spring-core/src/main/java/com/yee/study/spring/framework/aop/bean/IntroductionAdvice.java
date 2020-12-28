package com.yee.study.spring.framework.aop.bean;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * @author Roger.Yi
 */
@Slf4j
@Aspect
public class IntroductionAdvice {

    // "+" 号，表示只要是BizInterface及其子类都添加IAddition的实现方法
    @DeclareParents(value = "com.yee.study.spring.framework.aop.bean.BizInterface+", defaultImpl = AdditionServiceImpl.class)
    public AdditionInterface addition;
}
