package com.yee.study.java.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.ImmutableBean;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;

/**
 * CGLIB 示例
 *
 * All callback interfaces used by {@link Enhancer} extend this interface.
 * @see MethodInterceptor
 * @see NoOp
 * @see LazyLoader
 * @see Dispatcher
 * @see InvocationHandler
 * @see FixedValue
 *
 * @author Roger.Yi
 */
@Slf4j
public class CglibSample {

    /**
     * 使用MethodInterceptor作为切入
     */
    private static void testMethodInterceptor() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleBean.class);
        enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> {
            log.info("before method run...");
            Object result = proxy.invokeSuper(obj, args1);
            log.info("after method run...");
            return result;
        });
        SampleBean sample = (SampleBean) enhancer.create();
        sample.test();
    }

    /**
     * 固定值，所有方法返回固定的值，但是不会拦截final方法（例如：getClass()）
     */
    private static void testFixValue() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleBean.class);
        enhancer.setCallback(new FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "Hello cglib";
            }
        });
        SampleBean proxy = (SampleBean) enhancer.create();
        proxy.test(); //拦截test，输出Hello cglib
        log.info("toString={}", proxy.toString());
        log.info("getClass={}", proxy.getClass());
        log.info("hashCode={}", proxy.hashCode()); // 报错，固定返回String，但是hashCode要求返回int
    }

    /**
     * 使用InvocationHandler
     *
     * @throws Exception
     */
    private static void testInvocationHandler() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleBean.class);
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getDeclaringClass() != Object.class && method.getReturnType() == String.class) {
//                    method.invoke(proxy, args) 这里不能使用invoke，不然会引起死循环，因为invoke中调用的任何原代理类方法，均会重新代理到invoke方法中
                    // 需要使用MethodInterceptor
                    return "hello cglib";
                } else {
                    throw new RuntimeException("Do not know what to do");
                }
            }
        });
        SampleBean proxy = (SampleBean) enhancer.create();
        assertEquals("hello cglib", proxy.test2());
    }

    /**
     * 创建不可变Bean
     * @throws Exception
     */
    private static void testImmutableBean() throws Exception{
        SampleBean bean = new SampleBean();
        bean.setName("Roger");
        SampleBean immutableBean = (SampleBean) ImmutableBean.create(bean); //创建不可变类
        assertEquals("Roger", immutableBean.getName());
        bean.setName("Phoebe"); // 可以通过底层对象来进行修改
        assertEquals("Phoebe", immutableBean.getName());
        immutableBean.setName("Hello cglib"); //直接修改将throw exception
    }

    public static void main(String[] args) throws Exception {
        testMethodInterceptor();
//        testFixValue();
        testInvocationHandler();
//        testImmutableBean();
    }
}
