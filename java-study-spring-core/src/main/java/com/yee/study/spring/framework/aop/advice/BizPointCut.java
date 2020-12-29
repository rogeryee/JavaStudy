package com.yee.study.spring.framework.aop.advice;

import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;

import java.lang.reflect.Method;

/**
 * 自定义业务PointCut定义类
 *
 * @author Roger.Yi
 */
public class BizPointCut implements Pointcut {

    private BizClassFilter bizClassFilter = new BizClassFilter();

    private BizMethodMatcher bizMethodMatcher = new BizMethodMatcher();

    @Override
    public ClassFilter getClassFilter() {
        return bizClassFilter;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return bizMethodMatcher;
    }

    class BizClassFilter implements ClassFilter {
        @Override
        public boolean matches(Class<?> clazz) {
            return true;
        }
    }

    /**
     * 匹配包含Biz的方法
     */
    class BizMethodMatcher implements MethodMatcher {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            return method.getName().contains("Biz");
        }

        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }
}
