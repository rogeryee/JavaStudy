package com.yee.study.spring.framework.ioc;

import com.yee.study.spring.framework.ioc.bean.MessageService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;

/**
 * BeanFactory 示例
 *
 * @author Roger.Yi
 */
public class BeanFactorySample {

    private static final Logger log = LoggerFactory.getLogger(BeanFactorySample.class);

    /**
     * spring-ioc-simple.xml 配置了一个Bean - MessageService
     */
    @Test
    public void testIocSimple() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:ioc/spring-ioc-simple.xml"});
        log.info("context started.");

        // 从 context 获取MessageService的实例
        MessageService messageService = context.getBean(MessageService.class);

        // 这句将输出: hello world
        assertEquals("hello world", messageService.getMessage());
    }

    /**
     * spring-ioc-instantiation-processor.xml 配置了一个Bean - MyInstantiationAwareBeanPostProcessor
     * MyInstantiationAwareBeanPostProcessor 中针对MessageService类型构建了一个实例；
     * 由于是InstantiationAwareBeanPostProcessor是在Bean被初始化之前调用，所以构建后将不会调用MessageServiceImpl的构造器构建Bean
     */
    @Test
    public void testInstantiationProcessor() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:ioc/spring-ioc-instantiation-processor.xml"});
        log.info("context started.");

        // 从 context 获取MessageService的实例
        MessageService messageService = context.getBean(MessageService.class);

        // 这句将输出: hello world
        assertEquals("hello world from MyInstantiationAwareBeanPostProcessor", messageService.getMessage());
    }
}
