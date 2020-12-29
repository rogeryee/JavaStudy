package com.yee.study.spring.framework.aop;

import com.yee.study.spring.framework.aop.advice.IntroductionAdvice;
import com.yee.study.spring.framework.aop.bean.AdditionInterface;
import com.yee.study.spring.framework.aop.bean.BizInterface;
import com.yee.study.spring.framework.aop.bean.BizServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * AOP 测试类
 *
 * @author Roger.Yi
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AopConfig.class)
public class IntroductionSample {

    private static final Logger log = LoggerFactory.getLogger(IntroductionSample.class);

    /**
     * 基于@DeclareParents，实现对多个接口代理
     */
    @Test
    public void testIntroduction() {
        // 基于BizServiceImpl的代理
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(new BizServiceImpl());
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) ->
                log.info("Advice injected：method=[{}], args=[{}]", method.getName(), Arrays.asList(args)));
        proxyFactory.addAspect(new IntroductionAdvice()); // 添加切面

        // 获取代理接口
        BizInterface proxy = proxyFactory.getProxy();

        // 调用BizInterface#doBiz方法
        assertEquals("do biz", proxy.doBiz());

        // 通过@DeclareParents，代理类也实现了AdditionInterface#doAdditional()方法
        assertEquals("do addition", ((AdditionInterface) proxy).doAdditional());
    }
}
