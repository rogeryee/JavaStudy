package com.yee.study.spring.framework.aop;

import com.yee.study.spring.framework.aop.bean.AdditionInterface;
import com.yee.study.spring.framework.aop.bean.AnotherBizService;
import com.yee.study.spring.framework.aop.bean.BizInterface;
import com.yee.study.spring.framework.aop.bean.BizServiceImpl;
import com.yee.study.spring.framework.aop.bean.IntroductionAdvice;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.SpringProxy;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DecoratingProxy;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.objenesis.SpringObjenesis;
import org.springframework.objenesis.instantiator.ObjectInstantiator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * AOP 测试类
 *
 * @author Roger.Yi
 */
@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AopConfig.class)
public class AopTest {

    /**
     * 以下从上而下的介绍下ProxyFactory的父接口和父类：
     * 1. TargetClassAware： 该接口是暴露代理的目标类的最小实现。
     * 2. Advised： TargetClassAware的子类，持有一个AOP代理工厂配置的实现，配置包拦截器、其他的通知、顾问、代理的接口。Spring的任何AOP代理都能强转为此接口，这样就能够允许操作aop的通知。
     * 3. ProxyConfig：方便超类对于创建代理的配置，确保所以的代理创建对象有一致的属性。
     * 4. AdvisedSupport：aop代理配置管理的基类。自身不是aop代理，但其子类是能够直接获取aop代理实例的常规工厂。该类为子类管理创建Advices和Advisors便利，但它实际上没有实现代理的创建方法，由子类各自提供。
     * 5. ProxyCreatorSupport：AdvisedSupport的子类，代理工厂的基类，提供方便的访问一个可配置的AopProxyFactory。
     */
    @Test
    public void testProxyFactoryWithJdkProxy() throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory(new BizServiceImpl());
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) ->
                log.info("Advice injected 1：method=[{}], args=[{}]", method.getName(), Arrays.asList(args)));
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) ->
                log.info("Advice injected 2：method=[{}], args=[{}]", method.getName(), Arrays.asList(args)));

        // Spring构建AOP代理对象时，会默认增加三个需要实现的接口：SpringProxy，Advised和DecoratingProxy。
        // 1. SpringProxy：该接口没有任何方法，主要用于标识当前对象是Spring生成的代理对象；
        // 2. Advised：用于封装生成代理对象所需要的所有信息；
        // 3. DecoratingProxy：其有一个getDecoratedClass()方法，用于返回当前代理对象的目标对象的Class类型
        BizInterface proxy = (BizInterface) proxyFactory.getProxy();
        proxy.doBiz();

        log.info("proxyFactory.getTargetClass()={}", proxyFactory.getTargetClass()); // class com.yee.study.spring.framework.aop.bean.BizServiceImpl
        log.info("proxyFactory.getTargetSource()={}", proxyFactory.getTargetSource()); // SingletonTargetSource for target object [com.yee.study.spring.framework.aop.bean.BizServiceImpl@740abb5]
        log.info("proxyFactory.getProxiedInterfaces()={}", Arrays.asList(proxyFactory.getProxiedInterfaces())); // [interface com.yee.study.spring.framework.aop.bean.BizInterface]
        log.info("proxyFactory.getAdvisors()={}", Arrays.asList(proxyFactory.getAdvisors())); // [org.springframework.aop.support.DefaultPointcutAdvisor: pointcut [Pointcut.TRUE]; advice [com.yee.study.spring.framework.aop.ProxyTest$$Lambda$50/82825098@560cbf1a], org.springframework.aop.support.DefaultPointcutAdvisor: pointcut [Pointcut.TRUE]; advice [com.yee.study.spring.framework.aop.ProxyTest$$Lambda$51/1151512955@5fe8b721]]

        // 获取类型，看看是JDK代理还是cglib的代理
        log.info("proxy instanceof Proxy={}", proxy instanceof Proxy); //true, 若是由JDK动态代理生成的代理类，一定是Proxy的子类
        log.info("proxy instanceof SpringProxy={}", proxy instanceof SpringProxy); // true
        log.info("proxy.getClass()={}", proxy.getClass()); // class com.sun.proxy.$Proxy26
        log.info("isProxyClass={}", Proxy.isProxyClass(proxy.getClass())); // true
        log.info("isCglibProxy={}", AopUtils.isCglibProxy(proxy)); // false，不是cglib生成的代理类

        // 测试Advised接口、DecoratingProxy的内容
        Advised advised = (Advised) proxy; // Spring的任何AOP代理都能强转为此接口，在getProxy时已经默认实现了Adviced接口
        log.info("advised.getProxiedInterfaces()={}", Arrays.asList(advised.getProxiedInterfaces())); // [interface com.yee.study.spring.framework.aop.bean.BizInterface]
        log.info("advised.getAdvisors()={}", Arrays.asList(advised.getAdvisors())); // [org.springframework.aop.support.DefaultPointcutAdvisor: pointcut [Pointcut.TRUE]; advice [com.yee.study.spring.framework.aop.AopTest$$Lambda$53/288919635@e24ddd0], org.springframework.aop.support.DefaultPointcutAdvisor: pointcut [Pointcut.TRUE]; advice [com.yee.study.spring.framework.aop.AopTest$$Lambda$54/1851255134@6f70f32f]]
        log.info("advised.isExposeProxy()={}", advised.isExposeProxy()); // false，是否将proxy暴露到上下文AopContext，默认为false
        log.info("advised.isFrozen()={}", advised.isFrozen()); // false，动态代理配置是否不允许修改，和优化有关，默认为false

        DecoratingProxy decoratingProxy = (DecoratingProxy) proxy;
        log.info("DecoratedClass={}", decoratingProxy.getDecoratedClass()); // class com.yee.study.spring.framework.aop.bean.BizServiceImpl

        // Object的方法 ==== 所有的Object方法都不会被AOP代理 这点需要注意
        log.info("equals()={}", proxy.equals(new Object()));
        log.info("hashCode()={}", proxy.hashCode());
        log.info("getClass()={}", proxy.getClass());
        log.info("toString()={}", proxy.toString()); // 只有toString()被拦截

        // 获取方法
        Method m = proxy.getClass().getMethod("doBiz"); // 代理类 public final void com.sun.proxy.$Proxy21.doBiz()
        Method msm = AopUtils.getMostSpecificMethod(m, AopUtils.getTargetClass(proxy));
        // method=public final void com.sun.proxy.$Proxy21.doBiz()
        // mostSpecificMethod=public void com.yee.study.spring.framework.aop.bean.BizServiceImpl.doBiz()
        log.info("proxy method={}, mostSpecificMethod={}", m, msm);
    }

    /**
     * 测试Cglib代理
     * <p>
     * 关于final方法
     * - JDK代理：因为接口的方法不能使用final关键字，所以编译器就过不去
     * - CGLIB代理：final修饰某个方法后，不报错。但也不会拦截了
     * <p>
     * 关于static方法
     * - JDK代理：static修饰接口上的方法，要求有body体(JDK8后支持)。但是因为子类不能@Override了，所以编译就报错了
     * - CGLIB代理：父类方法用static修饰后，子类也是无法进行重写的。因此不抱错，但也不会拦截了
     * 使用代理的时候，尽量不要使用final和static关键字
     * <p>
     * 关于非public方法
     * - JDK代理：接口中的方法都是public的，所以对于它不存在这种现象
     * - CGLIB代理：记住结论 只有private的方法不能被代理（因为子类无法访问），其余的访问权限级别的，都能够被正常代理。 简单的说就是只要子类能够访问的权限，都能够被正常代理
     */
    @Test
    public void testProxyFactoryWithCglibProxy() throws Exception {
        ProxyFactory proxyFactory = new ProxyFactory(new AnotherBizService());
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) ->
                log.info("Advice injected：method=[{}], args=[{}]", method.getName(), Arrays.asList(args)));

        AnotherBizService proxy = (AnotherBizService) proxyFactory.getProxy();
        proxy.doMoreBiz();
        log.info("isCglibProxy={}", AopUtils.isCglibProxy(proxy)); // true，cglib生成的代理类

        // 获取方法
        Method m = proxy.getClass().getMethod("doMoreBiz");
        Method msm = AopUtils.getMostSpecificMethod(m, AopUtils.getTargetClass(proxy));
        // method=public final void com.yee.study.spring.framework.aop.bean.AnotherBizService$$EnhancerBySpringCGLIB$$b5adf14e.doMoreBiz()
        // mostSpecificMethod=public void com.yee.study.spring.framework.aop.bean.AnotherBizService.doMoreBiz()
        log.info("proxy method={}, mostSpecificMethod={}", m, msm);
    }

    /**
     * 使用Objeneseis构建的对象，属性都是不会被初始化的
     * <p>
     * Objenesis Vs class.newInstance
     * class构造器需要参数，而Objenesis可以不需要。
     * Objenesis主要应用场景：
     * 1. 序列化，远程调用和持久化 -对象需要实例化并存储为到一个特殊的状态，而没有调用代码
     * 2. 代理，AOP库和Mock对象 -类可以被子类继承而子类不用担心父类的构造器。
     * 3. 容器框架 -对象可以以非标准的方式被动态实例化（比如Spring就是容器框架）。
     */
    @Test
    public void testObjenesis() {
        Objenesis objenesis = new ObjenesisStd();
        AnotherBizService obj = objenesis.newInstance(AnotherBizService.class); // 使用newInstance获取实例
        log.info("obj name={}, id={}", obj.name, obj.id);

        ObjectInstantiator<AnotherBizService> instantiator = objenesis.getInstantiatorOf(AnotherBizService.class);
        AnotherBizService obj2 = instantiator.newInstance(); // 使用ObjectInstantiator获取实例（类似实例工厂）
        AnotherBizService obj3 = instantiator.newInstance();
        log.info("obj2 name={}, id={}", obj2.name, obj2.id);
        log.info("obj3 name={}, id={}", obj3.name, obj3.id);
        log.info("obj2 == obj3 : {}", obj2 == obj3);
    }

    /**
     * 使用Objeneseis构建的对象，属性都是不会被初始化的
     */
    @Test
    public void testSpringObjenesis() {
        Objenesis objenesis = new SpringObjenesis();
        AnotherBizService obj = objenesis.newInstance(AnotherBizService.class); // 使用newInstance获取实例
        log.info("obj name={}, id={}", obj.name, obj.id);
    }

    @Test
    public void testAspectJExecution() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* com.yee.study.spring.framework.aop.bean.*.doBiz())");
        assertTrue(AopUtils.canApply(pointcut, BizInterface.class));
        assertTrue(AopUtils.canApply(pointcut, BizServiceImpl.class));

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setPointcut(pointcut);
        advisor.setAdvice((MethodBeforeAdvice) (method, args, target) -> log.info("Before method={} invoked.", method));

        ProxyFactory proxyFactory = new ProxyFactory(new BizServiceImpl());
        proxyFactory.addAdvisor(advisor);
        BizInterface proxy = (BizInterface) proxyFactory.getProxy();
        proxy.doBiz();
        proxy.cancelBiz();
    }

    @Test
    public void testIntroduction() {
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(new BizServiceImpl());
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) ->
                log.info("Advice injected：method=[{}], args=[{}]", method.getName(), Arrays.asList(args)));
        proxyFactory.addAspect(new IntroductionAdvice());
        BizInterface proxy = proxyFactory.getProxy();
        proxy.doBiz();
        ((AdditionInterface) proxy).doAdditional(); // 通过@DeclareParents可以实现多个接口的代理
    }
}
