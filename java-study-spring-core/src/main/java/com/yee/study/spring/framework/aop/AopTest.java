package com.yee.study.spring.framework.aop;

import com.yee.study.spring.framework.aop.bean.AnotherBizService;
import com.yee.study.spring.framework.aop.bean.BizInterface;
import com.yee.study.spring.framework.aop.bean.BizServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.SpringProxy;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.DecoratingProxy;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.objenesis.SpringObjenesis;
import org.springframework.objenesis.instantiator.ObjectInstantiator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * AOP 测试类
 *
 * @author Roger.Yi
 */
@Slf4j
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AopConfig.class)
public class AopTest {

//    @Autowired
//    private BizInterface bizService;
//
//    @Autowired
//    private BizComponent bizComponent;

    /**
     * 以下从上而下的介绍下ProxyFactory的父接口和父类：
     * 1. TargetClassAware： 该接口是暴露代理的目标类的最小实现。
     * 2. Advised： TargetClassAware的子类，持有一个AOP代理工厂配置的实现，配置包拦截器、其他的通知、顾问、代理的接口。Spring的任何AOP代理都能强转为此接口，这样就能够允许操作aop的通知。
     * 3. ProxyConfig：方便超类对于创建代理的配置，确保所以的代理创建对象有一致的属性。
     * 4. AdvisedSupport：aop代理配置管理的基类。自身不是aop代理，但其子类是能够直接获取aop代理实例的常规工厂。该类为子类管理创建Advices和Advisors便利，但它实际上没有实现代理的创建方法，由子类各自提供。
     * 5. ProxyCreatorSupport：AdvisedSupport的子类，代理工厂的基类，提供方便的访问一个可配置的AopProxyFactory。
     */
    @Test
    public void testProxyFactoryWithJdkProxy() {
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
    }

    /**
     * 测试Cglib代理
     *
     * 关于final方法
     * - JDK代理：因为接口的方法不能使用final关键字，所以编译器就过不去
     * - CGLIB代理：final修饰某个方法后，不报错。但也不会拦截了
     *
     * 关于static方法
     * - JDK代理：static修饰接口上的方法，要求有body体(JDK8后支持)。但是因为子类不能@Override了，所以编译就报错了
     * - CGLIB代理：父类方法用static修饰后，子类也是无法进行重写的。因此不抱错，但也不会拦截了
     * 使用代理的时候，尽量不要使用final和static关键字
     *
     * 关于非public方法
     * - JDK代理：接口中的方法都是public的，所以对于它不存在这种现象
     * - CGLIB代理：记住结论 只有private的方法不能被代理（因为子类无法访问），其余的访问权限级别的，都能够被正常代理。 简单的说就是只要子类能够访问的权限，都能够被正常代理
     */
    @Test
    public void testProxyFactoryWithCglibProxy() {
        ProxyFactory proxyFactory = new ProxyFactory(new AnotherBizService());
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args, target) ->
                log.info("Advice injected：method=[{}], args=[{}]", method.getName(), Arrays.asList(args)));

        AnotherBizService proxy = (AnotherBizService) proxyFactory.getProxy();
        proxy.doMoreBiz();
        log.info("isCglibProxy={}", AopUtils.isCglibProxy(proxy)); // true，cglib生成的代理类
    }

    /**
     * 使用Objeneseis构建的对象，属性都是不会被初始化的
     *
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

//还有一种创建代理实例的方式，就是我们只用Enhancer把Class类型创建出来，然后创建实例的工作交给Objenesis 这样我们就拜托了对构造函数的依赖
//
//    public static void main(String[] args) {
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(MyDemo.class);
//
//        // 如国实用createClass方式来创建代理的实例  是不能直接添加callback得
//        //enhancer.setCallback();
//        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
//        enhancer.setStrategy(new DefaultGeneratorStrategy());
//        enhancer.setCallbackFilter(new CallbackHelper(MyDemo.class, null) {
//            @Override
//            protected Object getCallback(Method method) {
//                return (MethodInterceptor) (o, method1, args1, methodProxy) -> {
//                    System.out.println(method1.getName() + "---方法拦截前");
//                    // 此处千万不能调用method得invoke方法，否则会死循环的 只能使用methodProxy.invokeSuper 进行调用
//                    //Object result = method.invoke(o, args1);
//                    Object result = methodProxy.invokeSuper(o, args1);
//                    System.out.println(method1.getName() + "---方法拦截后");
//                    return result;
//                };
//            }
//        });
//        enhancer.setCallbackTypes(new Class[]{MethodInterceptor.class});
//
//        // 这里我们只生成Class字节码，并不去创建对象
//        Class clazz = enhancer.createClass();
//        // 创建对象的操作交给
//        Objenesis objenesis = new SpringObjenesis();
//        MyDemo myDemo = (MyDemo) objenesis.newInstance(clazz);
//
//        System.out.println(myDemo);
//        System.out.println(myDemo.code);
//
//    }
//输出：
//com.fsx.maintest.MyDemo$$EnhancerBySpringCGLIB$$6558edaa@5700d6b1
//null
//1
//2
//3
//4
//5
//6
//7
//8
//9
//10
//11
//12
//13
//14
//15
//16
//17
//18
//19
//20
//21
//22
//23
//24
//25
//26
//27
//28
//29
//30
//31
//32
//33
//34
//35
//36
//这样即使你没有空的构造函数，我依然可议给你创建一个实例。
//
//CGLIB整个过程如下
//Cglib根据父类,Callback, Filter 及一些相关信息生成key
//然后根据key 生成对应的子类的二进制表现形式
//使用ClassLoader装载对应的二进制,生成Class对象,并缓存
//最后实例化Class对象,并缓存
//生成二进制Class的方法
//针对不同场景, CGlib准备了不同的Class生成方法
//
//二进制文件存在在哪儿？
//放在byte数组中,下面这行代码就截取于方法AbstractClassGenerator.create(Object key)
//
//byte[] b = strategy.generate(this);
//1
//然后通过 ReflectUtils.defineClass(className, b, loader)生成对应的Class实例,并缓存入cache2
//
//Cglib如何把二进制Load生成的Class
//上面说了，事ReflectUtils.defineClass这个方法：
//
//    public static Class defineClass(String className, byte[] b, ClassLoader loader) throws Exception {
//        return defineClass(className, b, loader, PROTECTION_DOMAIN);
//    }
//
//    public static Class defineClass(String className, byte[] b, ClassLoader loader, ProtectionDomain protectionDomain) throws Exception {
//        Object[] args;
//        Class c;
//        if (DEFINE_CLASS != null) {
//            args = new Object[]{className, b, new Integer(0), new Integer(b.length), protectionDomain};
//            c = (Class)DEFINE_CLASS.invoke(loader, args);
//        } else {
//            if (DEFINE_CLASS_UNSAFE == null) {
//                throw new CodeGenerationException(THROWABLE);
//            }
//
//            args = new Object[]{className, b, new Integer(0), new Integer(b.length), loader, protectionDomain};
//            c = (Class)DEFINE_CLASS_UNSAFE.invoke(UNSAFE, args);
//        }
//
//        Class.forName(className, true, loader);
//        return c;
//    }
//1
//2
//3
//4
//5
//6
//7
//8
//9
//10
//11
//12
//13
//14
//15
//16
//17
//18
//19
//20
//21
//22
//注意事项
//JDK代理只能针对实现了接口的类以反射的方式生成代理，而不能针对类 ，所以也叫接口代理。
//CGLIB是针对类实现代理的，主要对指定的类以字节码转换的方式（ASM框架）生成一个子类，并重写其中的方法。
//
//所以使用CGLIB做动态代理，必须要保证有一个空的构造函数。（那是之前，其实现在不需要了，因为我们有了Objenesis的帮助），但是类不能是Final的
//
//关于final方法
//- JDK代理：因为接口的方法不能使用final关键字，所以编译器就过不去
//- CGLIB代理：final修饰某个方法后，不报错。但也不会拦截了
//关于static方法
//- JDK代理：static修饰接口上的方法，要求有body体(JDK8后支持)。但是因为子类不能@Override了，所以编译就报错了
//- CGLIB代理：父类方法用static修饰后，子类也是无法进行重写的。因此不抱错，但也不会拦截了
//使用代理的时候，尽量不要使用final和static关键字
//
//关于非public方法
//- JDK代理：接口中的方法都是public的，所以对于它不存在这种现象
//- CGLIB代理：记住结论 只有private的方法不能被代理（因为子类无法访问），其余的访问权限级别的，都能够被正常代理。 简单的说就是只要子类能够访问的权限，都能够被正常代理
//关于代理出现的：类型转换常见错误
//java.lang.ClassCastException: com.sun.proxy.$Proxy7 cannot be cast to XXXXX
//这一看就知道是JDK的代理对象不能转换为xxx对象。这是由JDK动态代理可能导致的。比如：
//
//public class Main {
//
//    public static void main(String[] args) {
//        ProxyFactory proxyFactory = new ProxyFactory(new Demo());
//        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args1, target) -> {
//                    System.out.println("你被拦截了：方法名为：" + method.getName() + " 参数为--" + Arrays.asList(args1));
//                }
//        );
//
//        // 如果使用的是JDK的动态代理，这里若实现实现类接收，就报错：java.lang.ClassCastException: com.fsx.maintest.$Proxy0 cannot be cast to com.fsx.maintest.Demo
//        //Demo demo = (Demo) proxyFactory.getProxy();
//        DemoInter demo = (DemoInter) proxyFactory.getProxy();
//        //你被拦截了：方法名为：hello 参数为--[]
//        //this demo show
//        demo.hello();
//
//    }
//}
//
//// 不要再实现接口,就会用CGLIB去代理
//class Demo implements DemoInter{
//
//    @Override
//    public void hello() {
//        System.out.println("this demo show");
//    }
//}
//
//interface DemoInter{
//    void hello();
//}
//1
//2
//3
//4
//5
//6
//7
//8
//9
//10
//11
//12
//13
//14
//15
//16
//17
//18
//19
//20
//21
//22
//23
//24
//25
//26
//27
//28
//29
//30
//31
//这个例子就好比我们经常使用@Autowired自动注入的时候，建议注入接口（因为注入实现类且是JDK动态代理的话，是会出现类似错误的）
//
//    @Autowired
//    private HelloService helloService; // 建议注入接口而不是实现类
//    //@Autowired
//    //private HelloServiceImpl helloServiceImpl; //不建议直接注入实现类
//1
//2
//3
//4
//上面例子，若采用的是CGLIB动态代理，不管是用接口还是实现类，都不会有问题。因此可见总体来说，CGLIb还是更加强大一些的
//
//CGLIB动态代理 字段为null 导致的坑
//先不说别的，看下面例子：（一定让你摸不着头脑）
//
//public class Main {
//
//    public static void main(String[] args) {
//        ProxyFactory proxyFactory = new ProxyFactory(new Demo());
//        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args1, target) -> {
//                    System.out.println("你被拦截了：方法名为：" + method.getName() + " 参数为--" + Arrays.asList(args1));
//                }
//        );
//
//        Demo demo = (Demo) proxyFactory.getProxy();
//        //你被拦截了：方法名为：setAge 参数为--[10]
//        demo.setAge(10);
//
//        //你被拦截了：方法名为：getAge 参数为--[]
//        System.out.println(demo.getAge()); //10
//        System.out.println(demo.age); //null 对你没看错，这里是null
//        System.out.println(demo.findAge()); //null 对你没看错，这里是null
//
//    }
//}
//
//// 不要再实现接口,就会用CGLIB去代理
//class Demo {
//    public Integer age;
//
//    // 此处用final修饰了  CGLIB也不会代理此方法了
//    public final Integer findAge() {
//        return age;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//}
//1
//2
//3
//4
//5
//6
//7
//8
//9
//10
//11
//12
//13
//14
//15
//16
//17
//18
//19
//20
//21
//22
//23
//24
//25
//26
//27
//28
//29
//30
//31
//32
//33
//34
//35
//36
//37
//38
//what?what a fuck？
//是不是此时你有此种感觉：不再相信java了。明明我都用set把age赋值了，为毛我拿出来却仍旧为null呢？（但通过getAge()方法获取正常）
//
//如何解决？
//最直接也是最推荐的方案，就是遵从上面所说得规范，小心谨慎为事。
//另外，本处我也提供一个简单的工具，配置上就能就绝get/set的问题。（但知其然必须知其所以然，才能更好的解决一类问题，而不是这一个问题）。仅供参考：
//
//@Aspect
//@Order(Integer.MIN_VALUE)
//public class SetterAspect {
//
//    // 切所有的set方法
//    @After(value = "execution(* *.set*(*)) && args(value)", argNames = "value")
//    public void after(JoinPoint jp, Object value) {
//        Object proxy = jp.getThis();
//
//        // 拿到目标对象
//        Object target = jp.getTarget();
//
//        if (AopUtils.isAopProxy(proxy)) {//只有代理对象才需要处理
//
//            try {
//                Class<?> proxyClass = proxy.getClass();
//                Class<?> targetClass = target.getClass();
//                String methodName = jp.getSignature().getName();
//
//                Method m = BeanUtils.findDeclaredMethod(proxyClass, methodName, new Class[]{value.getClass()});
//                PropertyDescriptor descriptor = BeanUtils.findPropertyForMethod(m);
//                String propName = descriptor.getName();
//
//                // 调用目标对象的set方法
//                Field f = targetClass.getClass().getDeclaredField(propName);
//                if (f != null) {
//                    f.setAccessible(true);
//                    f.set(proxy, value);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();//记录好异常进行处理
//            }
//        }
//    }
//
//}
//1
//2
//3
//4
//5
//6
//7
//8
//9
//10
//11
//12
//13
//14
//15
//16
//17
//18
//19
//20
//21
//22
//23
//24
//25
//26
//27
//28
//29
//30
//31
//32
//33
//34
//35
//36
//解释：
//若了解CGLIB的原理，甚至看过它生成的代理类的源码的话，此原因就可一句道破。
//
//原理简述：假设有个类A，会在字节码的层面上动态生成一个类B并加载进JVM里面。B继承自A同时又有对A的引用，B会重写所有的A类里面的非Final、非private方法，从而可以在目标方法调用前后进行对应的增强了。
//
//本文中：demo.setAge(10);执行的是代理对象的setAge()方法，所以set进去的值是给了代理对象的，目标对象仍然我null。而我们findAge()方法因为我标注了final，因此不能被CGLIB代理，所以只能从目标对象里拿值。因此它也只能拿到null
//
//而我们调用的getAge()方法它被代理过，所以他能拿到正确的值：10。
//
//备注：若采用JDK动态代理不回存在此种现象，因为接口代理的都是方法。
//另外：建议若代理的时候，final慎用。同时大多数情况下字段还是private掉吧，然后暴露出get方法给外部调用比较好。
//
//总结
//JdkDynamicAopProxy 入口方法是动态代理的 invoke() 方法，CGLIB 使用的是 DynamicAdvisedInterceptor.intercept()方法
//
//JdkDynamicAopProxy使用的MethodInvocation 是： ReflectiveMethodInvocation 子类，
//CGLIB 使用的是CglibMethodInvocation
//
//它俩都是ProxyMethodInvocation接口的实现类。并且CglibMethodInvocation是继承自ReflectiveMethodInvocation的
//
//CGLib更适合代理不需要频繁实例化的类，而Spring绝大多数Bean都是单例的，因此在Spring AOP中我极力推荐使用CGLib，它的功能更强大些
//
//关注A哥
//Author	A哥(YourBatman)
//个人站点	www.yourbatman.cn
//E-mail	yourbatman@qq.com
//微 信	fsx641385712
//活跃平台
//公众号	BAT的乌托邦（ID：BAT-utopia）
//知识星球	BAT的乌托邦
//每日文章推荐	每日文章推荐
//BAT的乌托邦
//
//
//YourBatman
//
//
//博客专家
//专栏创作者
//BAT的乌托邦
//也许当我老了，也一样写代码。不为别的，只为爱好
//公众号：BAT的乌托邦
//亦可在这里和我交流：https://www.yourbatman.cn
//
//点赞
//6
//
//评论
//8
//
//分享
//
//收藏
//11
//
//举报
//订阅专栏
//关注
//一键三连
//
//做一个合格的程序猿之浅析Spring AOP源码（十五） 分析JdkDynamicAopProxy的invoke方法
//BazingaLyncc
// 7931
//上一节我们已经分析了Proxyfactorybean如何去生成一个目标对象的代理的，这一节我们将浅析一下代理的基于JDK动态代理核心的回调方法invoke的源代码 JdkDynamicAopProxy.java文件是实现了AopProxy和InvocationHandler这2个接口的 先讲AopProxy这个接口，如图所示，AopProxy接口就定义了2个方法 我们再看
//JdkDynamicAopProxy实现AOP
//weixin_34198583的博客
// 458
//代理类是由默认AOP代理工厂DefaultAopProxyFactory中createAopProxy方法产生的。如果代理对象是接口类型，则生成JdkDynamicAopProxy代理。下面来看看这个类构造器查看源码，可以看到JdkDynamicAopProxy是一个final类，不能被继承和实现。其实现了AopProxy, InvocationHandler, Serializable接口，如下...
//
//优质评论可以帮助作者获得更高权重
//表情包
//woailihui
//Hogantry:CGLIB表示这锅我不背，脱离Spring使用CGLIB测试后发现final的方法也是可以获取到父类的public属性值，之所以在Spring中获取不到，怀疑是Spring在使用CGLIB做AOP时使用了一个TargetSource的东东导致的，具体还得去细看下代码24天前回复
//点赞
//qq271859852
//神的力量:文中介绍CglibAopProxy的getProxy()方法中，有一句结论："生成出来的代理对象，Spring默认都给你实现了接口：SpringProxy、DecoratingProxy、Advised"。 这句话在Spring 4.3.17有问题，不知道博主是哪个版本，或者是漏看了 在我看的版本中，CglibAopProxy#getProxy(java.lang.ClassLoader) 生成的代理对象，实现的接口是根据该行代码：AopProxyUtils.completeProxiedInterfaces(this.advised)来确定，而此方法内部是不包含`DecoratingProxy`接口的1年前回复
//点赞
//f641385712
//爱码士爱码士YourBatman回复: 好的，已修改，多谢指正1年前回复
//点赞
//qq271859852
//神的力量回复YourBatman: 所以CglibAopProxy生成的proxy并没有实现DecoratingProxy接口，结论需要修正一下1年前回复
//点赞
//f641385712
//爱码士爱码士YourBatman回复: 嗯，还以为你说的没有DecoratingProxy这行代码。对的，如果传false，是不会去实现DecoratingProxy接口的，这个没毛病1年前回复
//点赞
//qq271859852
//神的力量回复YourBatman: 4.3.17，调用关系是 ProxyFactory#getProxy()->createAopProxy().getProxy()[CglibAopProxy]->getProxy(null)->CglibAopProxy代码第186行调用`AopProxyUtils.completeProxiedInterfaces(this.advised)`->completeProxiedInterfaces(advised, false)，这里的`false`的含义是:@param decoratingProxy whether to expose the {@link DecoratingProxy} interface1年前回复
//点赞
//f641385712
//爱码士爱码士YourBatman回复: 刚我看了4.3.17版本，有的。实现了DecoratingProxy接口的1年前回复
//点赞
//f641385712
//爱码士爱码士YourBatman回复: 我的Spring版本是5.1.61年前回复
//点赞
//spring--aop_1_源码分析之JdkDynamicAopProxy实现
//convict_EVA的专栏
// 858
//aop实现有两种方式 1. ProxyFactoryBean方式： 这种方式是通过配置实现 2. ProxyFactory方式：这种方式是通过编程实现 这里说ProxyFactoryBean ，先上一张ProxyFactoryBean的关系图，后面能用到。 示例代码： /** * 代理接口，如果不是接口使用CGLIB代理 */ public interface ProxyIn...
//springAOP的两种方式JdkDynamicAopProxy和Cglib2AopProxy源码解析
//lz710117239的博客
// 3151
//在springAOP中，创建动态代理有两种方式，jdk的动态代理与cglib。 jdk的动态代理在springAOP中使用的是JdkDynamicAopProxy而cglib在springAOP中使用的类是Cglib2AopProxy，而且创建代理使用的类加载器基本都是ContextClassLoader类加载器。 在springAOP中创建动态代理都是通过ProxyFactory的getPr
//
//
//Spring AOP底层实现原理(动态代理) - 寒武没有纪 - CSDN博客
//11-6
//AOP就是要对目标进行代理对象的创建,Spring AOP是基于动态代理的,基于两种动态代理机制:JDK动态代理和CGLIB动态代理。 动态代理和静态代理区别? 动态代理:在虚拟机内...
//Spring的代理模式及Spring AOP-JDKDynamicAopProxy_luo...
//8-28
//本博中关于Spring的文章:Spring IOC和AOP原理,Spring事务原理探究,Spring配置文件属性详解,Spring中的代理模式 代理模式:对其他对象提供一种代理以控制对这个对象的...
//JdkDynamicAopProxy
//weixin_33814685的博客
// 79
//2019独角兽企业重金招聘Python工程师标准>>> ...
//基于Jdk动态代理-JdkDynamicAopProxy
//lemon
// 117
//1、JdkDynamicAopProxy 基于JDK动态代理创建代理对象 JdkDynamicAopProxy实现了AopProxy, InvocationHandler，InvocationHandler是基于JDK动态代理必须实现的回调接口。执行流程如下： (1)目标本身并未覆写equal/hashCode方法。调用JdkDynamicAopProxy的equals/hashCode方法...
//Spring Aop之Jdk代理实现原理详解_爱宝贝丶的博客
//7-12
//在讲解Spring Aop如何实现动态代理之前,建议读者先去阅读本人上一篇文章Spring Aop之Cglib实现原理详解,因为Cglib代理和Jdk代理织入切面逻辑的方式是类似的,只是生成代理...
//spring aop的实现原理---其中之一的jdk代理 - u012067853的博客...
//11-20
//前段时间写的java设计模式--代理模式,最近在看Spring Aop的时候,觉得于代理模式应该有密切的联系,于是决定了解下Spring Aop的实现原理。 说起AOP就不得不说下OOP了...
//请问各位大侠：spring 采用注解方式管理事务 单步调试进入JdkDynamicAopProxy，不能进入被调用的方法中
//09-05
//请问各位大侠：spring 采用注解方式管理事务 单步调试进入JdkDynamicAopProxy，不能进入被调用的方法中， 我在action中的调用处Serializable id = loginServices.saveCusUser(cusUser);设置断点，[color=red][b]F5单步执行时，不能进入被调用的方法，而是进入了 JdkDynamicAopProxy，继续单步执行会提示 Source not found for $Proxy18.saveCusUser(CusUser) line: not available[/b][/color]但是并没有抛出异常，数据正确保存了，不知是何原因？？？ [color=red][b]（基于CGLIB也是一样问题） [/b][/color] 我的代码 Action 中 //此处调用services 方法，保存对象 Serializable id = [color=red][b]loginServices.saveCusUser(cusUser); [/b][/color] service 中： [color=red][b]@Transactional [/b][/color] @Service("loginServices") public class LoginServicesImpl implements ILoginServices{ @Autowired private LoginDao loginDao public Serializable saveCusUser(CusUser cusUser){ return loginDao.saveCusUser(cusUser); } } Dao： public Serializable saveCusUser(Object cusUser){ Serializable id = null; Session cur_session = null; try { cur_session = this.getCurrentSession(); id = cur_session.save(cusUser); } catch (HibernateException e) { e.printStackTrace(); } return id ; }
//JdkDynamicAopProxy-invoke方法-5
//qq_25605779的专栏
// 1663
//通过前面例子jdk动态代理生成代理对象的源码分析，已经可以很清楚的了解到代理对象是如何进行增强和回调的，接下来继续分析JdkDynamicAopProxy是如何进行回调的对目标对象进行增强的。同样，在JdkDynamicAopProxy中实现也了InvocationHandler接口中的invoke方法。在JdkDynamicAopProxy中生成代理对象时，通过调用Proxy.newProxyI
//【源码】Spring AOP 10 AopProxyFactory AopProxy_小水...
//10-30
//参考 【小家Spring】详解Spring AOP的底层代理JdkDynamicAopProxy和ObjenesisCglibAopProxy的源码分析(介绍CGLIB使用中的坑)点赞 评论 分享 收藏 手机看 打赏 关注 一键...
//spring AOP底层原理实现——jdk动态代理 - weixin_3369...
//6-26
//【SpringAOP】——JDK动态代理 阅读数 1588 Spring的动态代理有两种:一是JDK的动态代理(需要提供接口);另一个是cglib动态代理(通过修改字节码来实现代理)。大部分...
//Spring系列之AopProxy-JdkDynamicAopProxy
//lemon
// 377
///** * 查找被代理目标源接口是否已经定义equals和hashcode方法 */ private void findDefinedEqualsAndHashCodeMethods(Class&lt;?&gt;[] proxiedInterfaces) { for (Class&lt;?&gt; proxiedInterface : proxiedInterfaces) { Metho...
//JdkDynamicAopProxy 源码分析
//ai_xiangjuan的博客
// 450
//JdkDynamicAopProxy 介绍 此类是spring aop框架基于java jdk（java.lang.Proxy）代理的实现类。 JdkDynamicAopProxy实现了AopProxy接口及InvocationHandler接口 final class JdkDynamicAopProxy implements AopProxy, InvocationHandler, ...
//Spring Aop之Jdk代理实现原理详解 - weixin_34088838的...
//6-28
//在讲解Spring Aop如何实现动态代理之前,建议读者先去阅读本人上一篇文章Spring Aop之Cglib实现原理详解,因为Cglib代理和Jdk代理织入切面逻辑的方式是类似的,只是生成代理...
//Spring AOP的底层实现分析 - turbo_zone的博客 - CSDN博客
//12-2
//在之前的通过Spring AOP实现自定义注解一文中,使用切面的方式来对注解方法实现了AOP增强,下面从底层分析一下Spring框架是如何生成AOP代理对象的。 在使用@Aspect注解...
//©️2020 CSDN 皮肤主题: 代码科技 设计师:Amelia_0503 返回首页
//关于我们
//招贤纳士
//广告服务
//开发助手
//
//400-660-0108
//
//kefu@csdn.net
//
//在线客服
//工作时间 8:30-22:00
//公安备案号11010502030143
//京ICP备19004658号
//京网文〔2020〕1039-165号
//经营性网站备案信息
//北京互联网违法和不良信息举报中心
//网络110报警服务
//中国互联网举报中心
//家长监护
//Chrome商店下载
//©1999-2020北京创新乐知网络技术有限公司
//版权与免责声明
//版权申诉
//
//YourBatman
//
//
//码龄10年
// 企业员工
//455
//原创
//148
//周排名
//1051
//总排名
//144万+
//访问
//
//等级
//1万+
//积分
//4289
//粉丝
//2166
//获赞
//1722
//评论
//5473
//收藏
//领英
//GitHub
//脉脉勋章
//签到新秀
//技术圈认证（专家版）
//专栏达人
//持之以恒
//勤写标兵Lv4
//原力计划专属勋章
//原力探索
//原力突破
//学习力
//博客之星-前十名
//原力新人
//1024勋章
//1024达人勋章
//私信
//关注
//搜博主文章
//
//BAT的乌托邦
//欢 迎 来 到 ， BAT 的 乌 托 邦
//作者：A哥(YourBatman)
//个人博客： https://yourbatman.cn
//个人介绍： 一个大龄程序员坎坷生涯的自述
//付费专栏： Netflix OSS套件一站式学习
//
//一个前25年还不会写Hallo World的半残程序猿，中途改行大龄程序员一枚，寄希望于通过Java改命。关注我的公众号吧：
//
//（专注Java领域，只做有深度的专栏文章分享，拒绝浅尝辄止）
//
//stay hungry stay foolish
//
//同道中人可我好友，邀你入群一起探讨学习（纯技术群，禁止一切广告哦~）
//
//
//（备注"java群"字样，会邀请你入群）
//热门文章
//【小家java】Spring事务嵌套引发的血案---Transaction rolled back because it has been marked as rollback-only  78512
//【小家Spring】注意BeanPostProcessor启动时对依赖Bean的“误伤”陷阱（is not eligible for getting processed by all...）  55450
//IntelliJ IDEA 2020.1正式发布，你要的Almost都在这！  36657
//蚂蚁金服上市了，我不想努力了  34241
//【小家Java】Java第二API之apache的commons-lang3工具包史上最完整的讲解（书写优雅代码必备工具）  31907
//最新评论
//Spring Cloud 2020.0.0正式发布，再见了Netflix
//YourBatman: 哈哈，可以建议CSDN来个退款按钮
//
//你自我介绍说很懂Spring配置类，那你怎么解释这个现象？
//YourBatman: 多谢肯定，互相学习
//
//蚂蚁金服上市了，我不想努力了
//YourBatman: 高级自由
//
//4. 上新了Spring，全新一代类型转换机制
//兴趣使然的程序猿: 学习的道路上一起进步，也期待你的关注与支持！
//
//3. 搞定收工，PropertyEditor就到这
//兴趣使然的程序猿: 创作不易，写的不错 共勉~ 互关一个
//
//最新文章
//6. 抹平差异，统一类型转换服务ConversionService
//Spring Cloud 2020.0.0正式发布，再见了Netflix
//5. 穿过拥挤的人潮，Spring已为你制作好高级赛道
//2020
//12月
//8篇
//11月
//5篇
//10月
//1篇
//09月
//5篇
//08月
//6篇
//07月
//8篇
//06月
//5篇
//05月
//8篇
//04月
//38篇
//03月
//55篇
//02月
//39篇
//01月
//13篇
//2019年163篇2018年101篇
//目录
//JdkDynamicAopProxy
//CglibAopProxy
//ObjenesisCglibAopProxy
//Objenesis：另一种实例化对象的方式
//Enhancer：CGLIB增强器
//关注A哥
//
//分类专栏
//
//享学Ribbon
//27篇
//
//享学Hystrix
//21篇
//
//享学Eureka
//34篇
//
//享学Feign
//12篇
//
//享学Archaius
//17篇
//
//享学Jackson
//22篇
//
//A哥学类型转换
//6篇
//
//新特性
//7篇
//
//享学Spring Cloud Context
//2篇
//
//A哥学数据校验
//6篇
//
//A哥学Java
//2篇
//
//A哥学Jackson
//9篇
//
//技术管理
//1篇
//
//IDEA踩坑系列
//1篇
//
//Spring static关键字
//3篇
//
//Spring配置类
//6篇
//
//Spring static关键字
//
//享学Spring Cloud Commons
//
//享学Netflix
//1篇
//
//程序人生
//8篇
//
//Spring技术栈
//2篇
//
//享学Spring Boot
//3篇
//
//享学Spring MVC
//143篇
//
//享学Spring
//5篇
//
//享学Spring Cloud
//1篇
//
//享学MyBatis
//3篇
//
//享学Java
//113篇
//
//享学Thinking
//1篇
//
//享学MySQL
//1篇
//
//享学Algorithm
//1篇
//
//享学Interview
//3篇
//
//享学Redis
//
//享学Linux
//4篇
//CSDN官方插件，现在体验可获得永久免费去广告特权！
//前端热门技术之新版TypeScript入门学习到案例教程
//5月实惠大前端
//Java serialVersionUID 有什么作用？
//Asp.Net MVC控制器获取视图传值几种方式
//微信小程序开发全家桶：开发入门到获得广告收益
//项目管理工具（2）：甘特图理论与实战
//RPA中级课程之二
//时间格式化大小写含义(Java 计算时间差以及比较日期大小 )
//C#MVC基类实现事务处理思路
//Lambda表达式&Stream
//地表最全企业级微信小程序开发流程（帮你尽可能减少开发周期）
//织梦响应式个人博客资讯网站模板.zip
//立即下载
//算法设计与分析（fd）
//smartcar.rar
//立即下载
//[转载] python通过反射执行代码
//C语言程序设计读书笔记：结构
//Flink-Study-master.zip
//立即下载
//【P1911】无线通讯网 kruskal
//intel parallel studio xe 2013 license file
//立即下载
//剑雨Axure RP9 【实战演练】
//深度学习原理到实战(直播时间：1月25日---1月30日)
//Chrome浏览器 抢购秒杀插件秒杀助手
//立即下载
//C程序设计语言读书笔记：类型运算符与表达式
//A Review of Electronic Cigarette Use Behaviour Studies
//立即下载
//python双端队列之自己动手.py
//立即下载
//SpringCloud微服务架构基础知识概述
//go语言实战项目
//CRM客户关系SpringBoot2.4+MybatisPlus3实战
//机器学习实战(直播时间：1月11日---1月23日)
//产品经理和项目经理区别与联系
//[转载] Python进程——multiprocessing.Event()|Barrier（）
//BigDataArchitect-master
//立即下载
//面向全场景模块化设计，京东智联云自研服务器探索
//百度地图离线API 2.0(含示例,可完全断网访问)
//立即下载
//群联PS3111-S11-13主控芯片 开卡,量产
//立即下载
//页面没有出现滚动条时底部悬浮显示到最底部
//Flink从入门到精通
//android listview显示数据库中内容
//立即下载
//Pyhton零基础入门到爬虫实战
//
//
//举报@Test
//    public void testIsAop() {
//        // object instanceof SpringProxy && (Proxy.isProxyClass(object.getClass()) || ClassUtils.isCglibProxyClass(object.getClass()));
//        assertTrue(AopUtils.isAopProxy(bizService));
//
//        // 代理后的类 class com.yee.study.spring.framework.utils.aop.EmployeeComponent$$EnhancerBySpringCGLIB$$db8ff0ee
//        log.info("component.getClass()={}", bizComponent.getClass());
//
//        // 实际的类 class com.yee.study.spring.framework.utils.aop.EmployeeComponent
//        log.info("component.getTargetClass()={}", AopUtils.getTargetClass(bizComponent));
//    }
//
//    @Test
//    public void testMostSpecificMethod() throws Exception {
//        Method m = bizComponent.getClass().getMethod("doBiz");
//        // 代理类 public final void com.yee.study.spring.framework.utils.aop.EmployeeComponent$$EnhancerBySpringCGLIB$$fb642769.doBiz()
//        log.info("method={}", m);
//
//        Method om = AopUtils.getMostSpecificMethod(m, AopUtils.getTargetClass(bizComponent));
//        // 实际类 public void com.yee.study.spring.framework.utils.aop.EmployeeComponent.doBiz()
//        log.info("mostSpecificMethod={}", om);
//    }
//
//    @Test
//    public void testApply() {
//        // 判断一个切入点是否匹配一个类型
//        AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
//        pc.setExpression("execution(* com.yee.study.spring.framework.utils.aop.*Component.*(..))");
//        assertTrue(AopUtils.canApply(pc, BizComponent.class));
//        assertFalse(AopUtils.canApply(pc, BizInterface.class));
//
//        pc = new AspectJExpressionPointcut();
//        pc.setExpression("execution(* com.yee.study.spring.framework.utils.aop.*Component1.*(..))");
//        assertFalse(AopUtils.canApply(pc, BizComponent.class));
//    }
//
//    @Test
//    public void testIntroduction() {
//        // EmployeeAdvice 通过@DeclareParent声明了针对 IEmployeeService添加IAddition的方法
//        ((AdditionInterface) bizService).doAdditional();
//    }
}
