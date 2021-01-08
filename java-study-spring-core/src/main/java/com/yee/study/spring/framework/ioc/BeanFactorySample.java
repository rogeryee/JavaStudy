package com.yee.study.spring.framework.ioc;

import com.yee.study.spring.framework.ioc.bean.MessageService;
import com.yee.study.spring.framework.ioc.bean.Person;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
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
     * BeanPostProcessor 有两个方法 postProcessBeforeInitialization、postProcessAfterInitialization 分别会在构建实例后的分别调用。
     *
     * 若Bean实现了 InitializingBean，则 postProcessBeforeInitialization、postProcessAfterInitialization 会在 Bean的afterPropertiesSet 方法前后执行
     *
     * 配置了4个BeanPostProcessor （多个 BeanPostProcessor 可以通过实现 Ordered 接口进行顺序）
     * 1. MyBeanPostProcessor : 实现了 BeanPostProcessor 接口
     * 2. MyInstantiationBeanPostProcessor ： 实现了 InstantiationBeanPostProcessor 接口，postProcessBeforeInstantiation 方法会在构建Bean实例前执行（该方法若返回了非空对象，则不会进行后续实例的创建）
     * 3. MyMergedBeanDefinitionPostProcessor： 实现了 MergedBeanDefinitionPostProcessor 接口，postProcessMergedBeanDefinition 方法会在构建实例前执行，用于对BeanDefinition的操作。
     * 4. MyDestructionAwareBeanPostProcessor： 实现了 DestructionAwareBeanPostProcessor 接口，postProcessBeforeDestruction 方法会在实例销毁前前执行（requiresDestruction 方法若返回false，则不会执行）
     *
     * 输出如下：
     * MyInstantiationBeanPostProcessor.BeforeInstantiation beanName=person
     * Person.newInstance()
     * MyMergedBeanDefinitionPostProcessor.MergedBeanDefinition beanName=person
     * MyInstantiationBeanPostProcessor.postProcessAfterInstantiation beanName=person
     * MyInstantiationBeanPostProcessor.postProcessPropertyValues beanName=person
     * MyBeanPostProcessor.BeforeInitialization beanName=person
     * Person.afterPropertiesSet
     * MyBeanPostProcessor.AfterInitialization beanName=person
     * MyDestructionAwareBeanPostProcessor.BeforeDestruction beanName=person
     * Person.destroy
     */
    @Test
    public void testBeanProcessor() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:ioc/spring-ioc-bean-processor.xml"});

        Person person = context.getBean(Person.class);
        assertEquals("Roger", person.getName());
        assertEquals("Shanghai", person.getAddress());

        ((AbstractApplicationContext)context).registerShutdownHook(); // 注册关闭的hook
    }

    @Test
    public void testBeanFactoryProcessor() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:ioc/spring-ioc-bean-factory-processor.xml"});

        Person person = context.getBean(Person.class);
        assertEquals("Roger", person.getName());
        assertEquals("Shanghai", person.getAddress());

        ((AbstractApplicationContext)context).registerShutdownHook(); // 注册关闭的hook
    }
}
