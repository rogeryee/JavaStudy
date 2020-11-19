package com.yee.study.spring.framework.utils.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.DeclareParents;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Method;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Roger.Yi
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AopConfig.class)
public class AopUtilsTest {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private EmployeeComponent component;

    @Test
    public void testIsAop() {
        // object instanceof SpringProxy && (Proxy.isProxyClass(object.getClass()) || ClassUtils.isCglibProxyClass(object.getClass()));
        assertTrue(AopUtils.isAopProxy(employeeService));

        // 代理后的类 class com.yee.study.spring.framework.utils.aop.EmployeeComponent$$EnhancerBySpringCGLIB$$db8ff0ee
        log.info("component.getClass()={}", component.getClass());

        // 实际的类 class com.yee.study.spring.framework.utils.aop.EmployeeComponent
        log.info("component.getTargetClass()={}", AopUtils.getTargetClass(component));
    }

    @Test
    public void testMostSpecificMethod() throws Exception {
        Method m = component.getClass().getMethod("doBiz");
        // 代理类 public final void com.yee.study.spring.framework.utils.aop.EmployeeComponent$$EnhancerBySpringCGLIB$$fb642769.doBiz()
        log.info("method={}", m);

        Method om = AopUtils.getMostSpecificMethod(m, AopUtils.getTargetClass(component));
        // 实际类 public void com.yee.study.spring.framework.utils.aop.EmployeeComponent.doBiz()
        log.info("mostSpecificMethod={}", om);
    }

    @Test
    public void testApply() {
        // 判断一个切入点是否匹配一个类型
        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
        pc.setExpression("execution(* com.yee.study.spring.framework.utils.aop.*Component.*(..))");
        assertTrue(AopUtils.canApply(pc, EmployeeComponent.class));
        assertFalse(AopUtils.canApply(pc, IEmployeeService.class));

        pc = new AspectJExpressionPointcut();
        pc.setExpression("execution(* com.yee.study.spring.framework.utils.aop.*Component1.*(..))");
        assertFalse(AopUtils.canApply(pc, EmployeeComponent.class));
    }

    @Test
    public void testIntroduction() {
        // EmployeeAdvice 通过@DeclareParent声明了针对 IEmployeeService添加IAddition的方法
        ((IAddition)employeeService).doAdditional();
    }
}
