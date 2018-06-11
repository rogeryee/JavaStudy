package com.yee.study.spring.framework.ioc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * ioc示例
 *
 * @author Roger.Yi
 */
public class Sample
{
    public static void main(String[] args)
    {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:ioc/spring-ioc.xml"});

        // 1. Bean初始化的示例
        // 1-1. 构造器：用SampleBean的默认构造器来生成SampleBean
        SampleBean sampleBean1 = context.getBean("init-sampleBeanByConstructor", SampleBean.class);
        sampleBean1.sayHello();

        // 1-2. 静态工厂方法：用SampleBeanFactory中的静态方法createSampleBean来生成Bean
        SampleBean sampleBean2 = context.getBean("init-sampleBeanByStaticFactoryMethod", SampleBean.class);
        sampleBean2.sayHello();

        // 1-3. 普通工厂方法：用SampleBeanFactory中的非静态方法createSampleBeanInstance来生成Bean
        SampleBean sampleBean3 = context.getBean("init-sampleBeanByFactoryMethod", SampleBean.class);
        sampleBean3.sayHello();

        // 1-4 循环依赖：
//        CircularDepBeanA circularDepBeanA = context.getBean("circular-dep-BeanA", CircularDepBeanA.class);

        // 2. Bean依赖的示例
        // 2.1 idref/ref 示例
        SampleBeanWithProps sampleBean4 = context.getBean("di-bean-BeanWithProps", SampleBeanWithProps.class);
        System.out.println("sampleBean4 = " + sampleBean4.toString());

        // 2.2
        SingletonBeanA singletonBeanA1 = context.getBean("di-bean-SingletonBeanA", SingletonBeanA.class);
        SingletonBeanA singletonBeanA2 = context.getBean("di-bean-SingletonBeanA", SingletonBeanA.class);
        System.out.println("singletonBeanA1 == singletonBeanA2 is " + (singletonBeanA1 == singletonBeanA2)); // true，因为是单例
        
        NonSingletonBeanB nonSingletonBeanB1 = context.getBean("di-bean-NonSingletonBeanB", NonSingletonBeanB.class);
        NonSingletonBeanB nonSingletonBeanB2 = context.getBean("di-bean-NonSingletonBeanB", NonSingletonBeanB.class);
        System.out.println("nonSingletonBeanB1 == nonSingletonBeanB2 is " + (nonSingletonBeanB1 == nonSingletonBeanB2)); // false，因为是多例
    }
}
