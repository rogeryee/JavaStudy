package com.yee.study.spring.framework.ioc;

import com.yee.study.spring.framework.ioc.bean.City;
import com.yee.study.spring.framework.ioc.bean.CycleBean;
import com.yee.study.spring.framework.ioc.bean.MessageService;
import com.yee.study.spring.framework.ioc.bean.Person;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

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
     * <p>
     * 若Bean实现了 InitializingBean，则 postProcessBeforeInitialization、postProcessAfterInitialization 会在 Bean的afterPropertiesSet 方法前后执行
     * <p>
     * 配置了4个BeanPostProcessor （多个 BeanPostProcessor 可以通过实现 Ordered 接口进行顺序）
     * 1. MyBeanPostProcessor : 实现了 BeanPostProcessor 接口
     * 2. MyInstantiationBeanPostProcessor ： 实现了 InstantiationBeanPostProcessor 接口，postProcessBeforeInstantiation 方法会在构建Bean实例前执行（该方法若返回了非空对象，则不会进行后续实例的创建）
     * 3. MyMergedBeanDefinitionPostProcessor： 实现了 MergedBeanDefinitionPostProcessor 接口，postProcessMergedBeanDefinition 方法会在构建实例前执行，用于对BeanDefinition的操作。
     * 4. MyDestructionAwareBeanPostProcessor： 实现了 DestructionAwareBeanPostProcessor 接口，postProcessBeforeDestruction 方法会在实例销毁前前执行（requiresDestruction 方法若返回false，则不会执行）
     * <p>
     * 输出如下：
     * MyInstantiationBeanPostProcessor.BeforeInstantiation beanName=person
     * Person.newInstance()
     * MyMergedBeanDefinitionPostProcessor.MergedBeanDefinition beanName=person
     * MyInstantiationBeanPostProcessor.postProcessAfterInstantiation beanName=person
     * MyInstantiationBeanPostProcessor.postProcessPropertyValues beanName=person
     * MyBeanPostProcessor.BeforeInitialization beanName=person
     * Person.afterPropertiesSet
     * Person.init
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

        ((AbstractApplicationContext) context).registerShutdownHook(); // 注册关闭的hook
    }

    /**
     * 配置了2个 BeanFactoryPostProcessor 接口的实现类
     * 1. MyBeanDefinitionRegistryPostProcessor： 实现了 BeanDefinitionRegistryPostProcessor （BeanFactoryPostProcessor的扩展接口）
     * 2. MyBeanFactoryPostProcessor ：普通的BeanFactoryPostProcessor 实现类
     * <p>
     * MyBeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry
     * MyBeanDefinitionRegistryPostProcessor.postProcessBeanFactory
     * MyBeanFactoryPostProcessor.postProcessBeanFactory
     * Person.newInstance()
     * Person.afterPropertiesSet
     * Person.destroy
     */
    @Test
    public void testBeanFactoryProcessor() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:ioc/spring-ioc-bean-factory-processor.xml"});

        Person person = context.getBean(Person.class);
        assertEquals("Roger", person.getName());
        assertEquals("Shanghai", person.getAddress());

        ((AbstractApplicationContext) context).registerShutdownHook(); // 注册关闭的hook
    }

    /**
     * GenericApplicationContext 可以比较灵活的加载BeanDefinitionReader
     * 本例同时通过xml和properties文件加载了2个bean定义
     */
    @Test
    public void testGenericApplicationContext() {
        GenericApplicationContext context = new GenericApplicationContext();

        // 基于Xml定义
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(context);
        xmlReader.loadBeanDefinitions(new ClassPathResource("ioc/spring-ioc-simple.xml"));

        // 基于properties定义
        PropertiesBeanDefinitionReader propReader = new PropertiesBeanDefinitionReader(context);
        propReader.loadBeanDefinitions(new ClassPathResource("ioc/spring-bean.properties"));
        context.refresh();

        MessageService messageService = context.getBean("messageService", MessageService.class);
        assertEquals("hello world, Roger", messageService.getMessage());

        MessageService messageService2 = context.getBean("messageService2", MessageService.class);
        assertEquals("hello world, Phoebe", messageService2.getMessage());
    }

    /**
     * 测试通过factory-bean构建Bean实例
     */
    @Test
    public void testFactoryBean() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:ioc/spring-ioc-factory-bean.xml"});
        City city = context.getBean(City.class);
        assertEquals("Default", city.getName());
    }

    /**
     * 测试自定义的 LifecycleProcessor
     * 注意：只有自定义的beanName为 lifecycleProcessor 时，才会替换默认的LifecycleProcessor
     * <p>
     * SmartMessageServiceImpl 实现了 SmartLifecycle
     */
    @Test
    public void testLifecycleProcessor() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:ioc/spring-ioc-lifecycle-processor.xml"});
        log.info("context started.");
    }

    /**
     * 测试 单例对象的循环依赖（基于Setter方法）
     */
    @Test
    public void testCycleDepsSingleton() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:ioc/spring-ioc-cycle-deps-singleton.xml"});
        log.info("cycle-deps loaded.");
        CycleBean.CycleBeanA a = context.getBean(CycleBean.CycleBeanA.class);
        CycleBean.CycleBeanB b = context.getBean(CycleBean.CycleBeanB.class);
        CycleBean.CycleBeanC c = context.getBean(CycleBean.CycleBeanC.class);
    }

    /**
     * 测试 单例对象的循环依赖（基于构造器）
     * <p>
     * 会得到一个 BeanCurrentlyInCreationException，由此可见 Spring不支持基于构造器的循环依赖
     */
    @Test
    public void testCycleDepsSingletonConstructor() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:ioc/spring-ioc-cycle-deps-singleton-constructor.xml"});
        log.info("cycle-deps loaded.");
        CycleBean.CycleBeanA a = context.getBean(CycleBean.CycleBeanA.class);
        CycleBean.CycleBeanB b = context.getBean(CycleBean.CycleBeanB.class);
        CycleBean.CycleBeanC c = context.getBean(CycleBean.CycleBeanC.class);
    }

    /**
     * 测试 多例对象的循环依赖
     * <p>
     * 会得到一个 BeanCurrentlyInCreationException，由此可见 Spring不支持prototype对象的循环依赖
     */
    @Test
    public void testCycleDepsPrototype() {
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:ioc/spring-ioc-cycle-deps-prototype.xml"});
        log.info("cycle-deps-prototype loaded.");
        CycleBean.CycleBeanA a = context.getBean(CycleBean.CycleBeanA.class);
        CycleBean.CycleBeanB b = context.getBean(CycleBean.CycleBeanB.class);
        CycleBean.CycleBeanC c = context.getBean(CycleBean.CycleBeanC.class);
    }
}
