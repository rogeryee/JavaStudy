package com.yee.study.java.core.annotation;

import java.lang.reflect.Method;

/**
 * MyClassAnnotation、MyMethodAnnotation的示例
 *
 * @author Roger.Yi
 */
public class MyAnnotationSample {

    public static void main(String[] args) {

        Class<ClassWithAnno> classWithAnno = ClassWithAnno.class;
        if (classWithAnno.isAnnotationPresent(MyClassAnnotation.class)) {
            MyClassAnnotation classAnnotation = classWithAnno.getAnnotation(MyClassAnnotation.class);
            System.out.println("class annotation=" + classAnnotation.value() + ", toString=" + classAnnotation);

            Method[] methods = classWithAnno.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(MyMethodAnnotation.class)) {
                    MyMethodAnnotation methodAnnotation = method.getAnnotation(MyMethodAnnotation.class);
                    System.out.println("method annotation=" + methodAnnotation);
                }
            }
        }
    }
}

@MyClassAnnotation(value = "Hello")
class ClassWithAnno {

    @MyMethodAnnotation
    public void doSomething() {
        System.out.println("doSomething");
    }

    @MyMethodAnnotation(value = {"girl", "boy"})
    public void doOtherthing() {
        System.out.println("doOtherthing");
    }
}