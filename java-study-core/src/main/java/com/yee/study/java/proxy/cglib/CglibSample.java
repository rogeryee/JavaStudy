package com.yee.study.java.proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.beans.ImmutableBean;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.core.DefaultNamingPolicy;
import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.FixedValue;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.reflect.ConstructorDelegate;
import net.sf.cglib.reflect.MethodDelegate;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * CGLIB 示例
 * <p>
 * All callback interfaces used by {@link Enhancer} extend this interface.
 *
 * @author Roger.Yi
 * @see MethodInterceptor
 * @see NoOp
 * @see LazyLoader
 * @see Dispatcher
 * @see InvocationHandler
 * @see FixedValue
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
            Object result = proxy.invokeSuper(obj, args1); // invokeSuper调用的是已经被增强（Enhanced）的方法。
//            Object result = proxy.invoke(obj, args1); 此处不可使用invoke 会导致死循环。invoke方法调用的是被代理类的方法
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
     *
     * @throws Exception
     */
    private static void testImmutableBean() throws Exception {
        SampleBean bean = new SampleBean();
        bean.setName("Roger");
        SampleBean immutableBean = (SampleBean) ImmutableBean.create(bean); //创建不可变类
        assertEquals("Roger", immutableBean.getName());
        bean.setName("Phoebe"); // 可以通过底层对象来进行修改
        assertEquals("Phoebe", immutableBean.getName());
        immutableBean.setName("Hello cglib"); //直接修改将throw exception
    }

    interface SampleBeanDelegate {
        String getFullName();
    }

    /**
     * 关于Method.create的参数说明：
     * 1. 第二个参数为即将被代理的方法
     * 2. 第一个参数必须是一个无参数构造的bean。因此MethodDelegate.create并不是你想象的那么有用
     * 3. 第三个参数为只含有一个方法的接口。当这个接口中的方法被调用的时候，将会调用第一个参数所指向bean的第二个参数方法
     * <p>
     * 缺点：
     * 1. 为每一个代理类创建了一个新的类，这样可能会占用大量的永久代堆内存
     * 2. 你不能代理需要参数的方法
     * 3. 如果你定义的接口中的方法需要参数，那么代理将不会工作，并且也不会抛出异常；如果你的接口中方法需要其他的返回类型，那么将抛出IllegalArgumentException
     *
     * @throws Exception
     */
    private static void testMethodDelegate() throws Exception {
        SampleBean bean = new SampleBean();
        bean.setName("Hello cglib");
        SampleBeanDelegate delegate = (SampleBeanDelegate) MethodDelegate.create(bean, "getName", SampleBeanDelegate.class);
        assertEquals("Hello cglib", delegate.getFullName());
    }

    interface SampleBeanConstructorDelegate {
        Object newInstance(String value);
    }

    private static void testWithObjensis() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleBean.class);
        enhancer.setNamingPolicy(DefaultNamingPolicy.INSTANCE);
        enhancer.setStrategy(new DefaultGeneratorStrategy());
        enhancer.setCallbackFilter(new CallbackHelper(SampleBean.class, null) {
            @Override
            protected Object getCallback(Method method) {
                return (MethodInterceptor) (o, method1, args1, methodProxy) -> {
                    log.info("before method run...");
                    // 此处千万不能调用method得invoke方法，否则会死循环的 只能使用methodProxy.invokeSuper 进行调用
                    Object result = methodProxy.invokeSuper(o, args1);
                    log.info("after method run...");
                    return result;
                };
            }
        });
        enhancer.setCallbackTypes(new Class[]{MethodInterceptor.class});

        // 使用cglib生成Class字节码，并不去创建对象
        // 使用createClass方式，会忽略设置过的callback
        Class clazz = enhancer.createClass();

        // 创建对象的操作交给
        Objenesis objenesis = new ObjenesisStd();
        SampleBean bean = (SampleBean) objenesis.newInstance(clazz);
        log.info("testWithObjensis bean name={}", bean.getName());
        bean.test();
    }

    /**
     * 对构造函数进行代理
     *
     * @throws Exception
     */
    private static void testConstructorDelegate() throws Exception {
        SampleBeanConstructorDelegate constructorDelegate = (SampleBeanConstructorDelegate) ConstructorDelegate.create(
                SampleBean.class, SampleBeanConstructorDelegate.class);
        SampleBean bean = (SampleBean) constructorDelegate.newInstance("Hello world");
        assertTrue(SampleBean.class.isAssignableFrom(bean.getClass()));
        log.info(bean.getName());
    }

    public static void main(String[] args) throws Exception {
        // 调试cglib，输出动态代理生成的class所在路径
//        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/cntp/MyWork/yee/temp_classes");
        testMethodInterceptor();
//        testFixValue();
//        testInvocationHandler();
//        testImmutableBean();
//        testMethodDelegate();
//        testConstructorDelegate();
//        testWithObjensis();
    }
}
