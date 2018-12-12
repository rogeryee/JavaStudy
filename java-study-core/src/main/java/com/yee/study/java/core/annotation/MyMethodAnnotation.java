package com.yee.study.java.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于方法上的自定义的注解(Annotation)类
 *
 * @author Roger.Yi
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyMethodAnnotation {
    String[] value() default "unknown";
}
