package com.yee.study.spring.framework.aop;

import com.yee.study.spring.framework.aop.bean.BizInterface;
import com.yee.study.spring.framework.aop.bean.BizServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertTrue;

/**
 * PointCut 示例
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
}
