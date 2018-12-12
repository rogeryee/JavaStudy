package com.yee.study.java.core.jdk8;

import org.junit.Test;

/**
 * JDK 1.8 新特性示例
 * 
 * @author Roger.Yi
 */
public class NewFeatureSample {
    /**
     *  接口新增：默认方法与静态方法
     *  default 接口默认实现方法是为了让集合类默认实现这些函数式处理，而不用修改现有代码
     *  （List继承于Iterable<T>，接口默认方法不必须实现default forEach方法）
     */
    @Test
    public void testDefaultFunctionInterface(){
        //可以直接使用接口名.静态方法来访问接口中的静态方法
        JDK8Interface1.staticMethod();

        //接口中的默认方法必须通过它的实现类来调用
        new JDK8InterfaceImpl1().defaultMethod();

        //多实现类，默认方法重名时必须复写
        new JDK8InterfaceImpl2().defaultMethod();
    }
}

/**
 * JDK1.8开始接口中可以定义静态方法以及用default关键字修饰的普通方法
 */
interface JDK8Interface1 {
    
    public static void staticMethod(){
        System.out.println("JDK8Interface1.staticMethod");
    }
    
    public default void defaultMethod(){
        System.out.println("JDK8Interface1.defaultMethod");
    }
}

interface JDK8Interface2 {
    
    public static void staticMethod(){
        System.out.println("JDK8Interface2.staticMethod");
    }

    public default void defaultMethod(){
        System.out.println("JDK8Interface2.defaultMethod");
    }
}

class JDK8InterfaceImpl1 implements JDK8Interface1 {
    
}

class JDK8InterfaceImpl2 implements JDK8Interface1,JDK8Interface2 {

    /**
     * 接口1和接口2都有默认方法，所以实现接口后，默认方法名相同，必须复写默认方法
     */
    @Override
    public void defaultMethod() {
        JDK8Interface1.super.defaultMethod();
        JDK8Interface2.super.defaultMethod();
        System.out.println("JDK8InterfaceImpl2.defaultMethod");
    }
}
