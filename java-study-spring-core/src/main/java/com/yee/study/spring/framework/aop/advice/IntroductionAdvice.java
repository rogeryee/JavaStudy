package com.yee.study.spring.framework.aop.advice;

import com.yee.study.spring.framework.aop.bean.AdditionInterface;
import com.yee.study.spring.framework.aop.bean.AdditionServiceImpl;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

/**
 * 基于@DeclareParents的Aspect定义类
 *
 * @author Roger.Yi
 */
@Aspect
public class IntroductionAdvice {

    /**
     * 声明一个需要被代理的接口，目标是所有BizInterface接口以及其子类，为其增加AdditionInterface接口的代理（默认使用AdditionServiceImpl类的实现）
     * 注：AdditionServiceImpl必须有默认构造器，Spring会为@DeclareParents声明的切面创建一个 DelegatePerTargetObjectIntroductionInterceptor，对相应的方法进行拦截
     * <p>
     * "+" 号，表示只要是BizInterface及其子类都添加IAddition的实现方法
     */
    @DeclareParents(value = "com.yee.study.spring.framework.aop.bean.BizInterface+", defaultImpl = AdditionServiceImpl.class)
    public AdditionInterface addition;
}
