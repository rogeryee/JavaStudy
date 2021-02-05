package com.yee.study.spring.framework.aop;

import com.yee.study.spring.framework.aop.bean.Family;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * AOP 示例
 *
 * @author Roger.Yi
 */
public class AopSample {

    private static final Logger log = LoggerFactory.getLogger(AopSample.class);

    @Test
    public void testAopByXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:aop/spring-aop-simple.xml"});
        Family.Child child = context.getBean(Family.Child.class);
        child.eat();
    }
}
