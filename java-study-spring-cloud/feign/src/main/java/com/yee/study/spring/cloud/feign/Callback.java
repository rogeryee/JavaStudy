package com.yee.study.spring.cloud.feign;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Roger.Yi
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Callback {
}
