package com.yee.study.java.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于类上的自定义的注解(Annotation)类
 *
 * @author Roger.Yi
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyClassAnnotation {
    String value() default "unknown class";
}
