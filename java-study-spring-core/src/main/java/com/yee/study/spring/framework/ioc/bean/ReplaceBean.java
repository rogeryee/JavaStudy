package com.yee.study.spring.framework.ioc.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

/**
 * replace-method 示例Bean
 *
 * @author Roger.Yi
 */
@Slf4j
public class ReplaceBean {

    public String hello(Integer age) {
        log.info("ReplaceBean.helloAge(" + age + ")");
        return "helloAge";
    }

    public String hello(String name) {
        log.info("ReplaceBean.helloName(" + name + ")");
        return "helloName";
    }

    public String hi() {
        log.info("ReplaceBean.hi()");
        return "hi";
    }

    @Slf4j
    public static class HelloMethodReplacement implements MethodReplacer {
        @Override
        public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
            log.info("HelloMethodReplacement.reimplement(), method={}, args={}", method.getName(), args);
            return "new hello";
        }
    }

    @Slf4j
    public static class HiMethodReplacement implements MethodReplacer {
        @Override
        public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
            log.info("HiMethodReplacement.reimplement(), method={}, args={}", method.getName(), args);
            return "new hi";
        }
    }
}
