package com.yee.study.spring.framework.aop;

import com.yee.study.spring.framework.aop.advice.BizPointCut;
import com.yee.study.spring.framework.aop.bean.BizInterface;
import com.yee.study.spring.framework.aop.bean.BizServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * PointCut 示例
 * <p>
 * Pointcut接口作为SpringAop中对AOP的最顶层抽象，主要负责对系统的相应的Joinpoint进行捕捉。
 * 并且提供了一个TruePointcut实例，当Pointcut为TruePointcut类型时，则会忽略所有的匹配条件，对系统中所有的对象进行Joinpoint所定义的规则进行匹配；
 * <p>
 * ClassFilter与MethodMatcher分别用于在不同的级别上限定Joinpoint的匹配范围，满足不同粒度的匹配。
 * ClassFilter限定在类级别上，MethodMatcher限定在方法级别上；但是SpringAop主要支持在方法级别上的匹配，所以对类级别的匹配支持相对简单一些；
 * 当传入的clazz与Pointcut规定的类型一致时，则返回true，否则返回false,返回为true时，则表示对这个类进行植入操作，当类型对Joinpoint的匹配不产生影响的时候，可以让Pointcut接口中的ClassFilter getClassFilter()方法直接返回TrueClassFilter.INSTANCE,则表示对系统中的所有对象进行Joinpoint匹配；
 * <p>
 * MethodMatcher接口通过重载定义了两个matches()方法，两个参数的matches()被称为静态匹配，在匹配条件不是太严格时使用，可以满足大部分场景的使用，称之为静态的主要是区分为三个参数的matches()方法需要在运行时动态的对参数的类型进行匹配；两个方法的分界线就是boolean isRuntime()方法，进行匹配时先用两个参数的matches()方法进行匹配，若匹配成功，则检查boolean isRuntime()的返回值，若为true，则调用三个参数的matches()方法进行匹配（若两个参数的都匹配不中，三个参数的必定匹配不中），比如需要统计用户登录次数时，那么登录传入的参数就是可以忽略的，则调用两个参数的matches()方法就已经足够了，但是若要在登陆时对用户账号执行特殊的操作（如赋予特殊的操作权限），就需要对参数进行一个类似于检验的操作，就需要调用三个参数的matches()进行匹配。
 *
 * @author Roger.Yi
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AopConfig.class)
public class PointCutSample {

    private static final Logger log = LoggerFactory.getLogger(PointCutSample.class);

    /**
     * 测试基于AspectJ表达式切入
     */
    @Test
    public void testAspectJExpressionPointcut() {
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

    /**
     * 自定义Poincut示例
     *
     * 1. 添加了Advice (GeneralAdvice), 不指定Pointcut所以采用默认TruePintCut（匹配所有类和方法）
     * 2. 添加了BizAdvisor（使用自定义的BizPointCut)，匹配所有类中包含 "Biz" 的方法。
     */
    @Test
    public void testCustomizedPointCut() {
        ProxyFactory proxyFactory = new ProxyFactory(new BizServiceImpl());
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) -> log.info("GeneralAdvice method={} invoked.", method));

        DefaultPointcutAdvisor bizAdvisor = new DefaultPointcutAdvisor();
        bizAdvisor.setPointcut(new BizPointCut());
        bizAdvisor.setAdvice((MethodBeforeAdvice) (method, args, target) -> log.info("BizAdvice method={} invoked.", method));
        proxyFactory.addAdvisor(bizAdvisor);

        BizInterface proxy = (BizInterface) proxyFactory.getProxy();
        proxy.doBiz(); // 同时触发 GeneralAdvice、BizAdvice
        proxy.cancelBiz(); // 同时触发 GeneralAdvice、BizAdvice
        proxy.fallback(); // 只触发 GeneralAdvice
    }
}
