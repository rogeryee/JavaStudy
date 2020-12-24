package com.yee.study.spring.framework.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ClassUtils;

/**
 * Spring - ClassUtils 类示例
 *
 * @author Roger.Yi
 */
public class ClassUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(ClassUtilsTest.class);

    @Test
    public void testClassLoader() {
        log.info("Thread.currentThread().getContextClassLoader()={}", Thread.currentThread().getContextClassLoader());
        log.info("this.getClass().getClassLoader()={}", this.getClass().getClassLoader());
        log.info("ClassLoader.getSystemClassLoader()={}", ClassLoader.getSystemClassLoader());
        log.info("ClassLoader.getSystemClassLoader().getParent()={}", ClassLoader.getSystemClassLoader().getParent());
        log.info("ClassUtils.getDefaultClassLoader()={}", ClassUtils.getDefaultClassLoader());
    }

    @Test
    public void testClassForName() throws Exception {
        log.info("class forName int, {}", ClassUtils.forName("int", ClassUtils.getDefaultClassLoader()));
        log.info("class forName java.lang.String[], {}", ClassUtils.forName("java.lang.String[]", ClassUtils.getDefaultClassLoader()));
        log.info("class forName java.lang.Thread.State, {}", ClassUtils.forName("java.lang.Thread.State", ClassUtils.getDefaultClassLoader()));
        log.info("className of int=[{}]", ClassUtils.resolvePrimitiveClassName("int"));
    }
}
