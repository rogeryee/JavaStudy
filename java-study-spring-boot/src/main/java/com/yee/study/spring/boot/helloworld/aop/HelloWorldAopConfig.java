package com.yee.study.spring.boot.helloworld.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * 一个简单的AOP切面配置
 * 
 * @author Roger.Yi
 */
@Configuration
@Aspect
public class HelloWorldAopConfig {

    /**
     * 针对包 com.yee.study.spring.boot.helloworld.controller下的所有类的方法进行切面处理
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.yee.study.spring.boot.helloworld.controller.*.*(..))")
    public Object simpleAop(final ProceedingJoinPoint pjp) throws Throwable {
        try {
            Object[] args = pjp.getArgs();
            System.out.println("args : " + Arrays.asList(args));

            Object ret = pjp.proceed();
            System.out.println("ret = " + ret);
            return ret;
        } catch (Throwable t) {
            throw t;
        }
    }
}
