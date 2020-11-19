package com.yee.study.spring.framework.utils.clazz;

import org.junit.Test;
import org.springframework.util.ClassUtils;

/**
 * @author Roger.Yi
 */
public class ClassUtilsTest {

    @Test
    public void testClassLoader() {
        System.out.println(Thread.currentThread().getContextClassLoader());
        System.out.println(this.getClass().getClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader());
        System.out.println(ClassLoader.getSystemClassLoader().getParent());
    }

    @Test
    public void testClassforName() throws Exception{
        System.out.println(ClassUtils.forName("int",ClassUtils.getDefaultClassLoader()));
        System.out.println(ClassUtils.forName("java.lang.String[]",ClassUtils.getDefaultClassLoader()));
        System.out.println(ClassUtils.forName("java.lang.Thread.State",ClassUtils.getDefaultClassLoader()));

        System.out.println(ClassUtils.resolvePrimitiveClassName("int"));
    }
}
