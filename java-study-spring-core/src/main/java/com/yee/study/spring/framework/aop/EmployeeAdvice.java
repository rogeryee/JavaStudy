package com.yee.study.spring.framework.aop;

import com.yee.study.spring.framework.aop.bean.AdditionInterface;
import com.yee.study.spring.framework.aop.bean.AdditionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * @author Roger.Yi
 */
@Slf4j
//@Component
//@Aspect
public class EmployeeAdvice {

//    @Before("execution(* com.yee.study.spring.framework.aop.*Service.*(..))")
//    public void advisor() {
//      log.info("do before");
//    }
//
//    @Before("execution(* com.yee.study.spring.framework.aop.*Component.*(..))")
//    public void advisor2() {
//        log.info("do before");
//    }
//
//    // "+" 号，表示只要是IEmployeeService及其子类都添加IAddition的实现方法
//    @DeclareParents(value = "com.yee.study.spring.framework.aop.bean.BizInterface+", defaultImpl = AdditionServiceImpl.class)
//    public AdditionInterface addition;
}
