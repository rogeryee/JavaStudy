package com.yee.study.java.proxy.objenesis;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisBase;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;
import org.objenesis.strategy.InstantiatorStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * Objenesis 使用示例
 *
 * Objenesis是一个小的Java库，它有一个用途：实例化一个特定类的新对象。
 *
 * 使用场景：
 * Java已经支持使用class.newinstance()的类动态实例化，但是必须要有一个合适的构造函数。而很多场景下类不能够用这种方式去实例化，例如：
 *
 * 构造函数需要参数（Constructors that require arguments）
 * 有副作用的构造函数（Constructors that have side effects）
 * 会抛出异常的构造函数（Constructors that throw exceptions）
 *
 * 在Objenesis中有两个主要接口：
 *
 * ObjectInstantiator-实例化一个类的多个实例
 *
 * interface ObjectInstantiator {
 *   Object newInstance();
 * }
 * InstantiatorStrategy-实例化一个类的特定策略(对于不同类型的类来说，这是不同的)
 *
 * interface InstantiatorStrategy {
 *   ObjectInstantiator newInstantiatorOf(Class type);
 * }
 * 所有的Objenesis类都在org.objenesis包中。
 * </pre>
 *
 * @author Roger.Yi
 */
public class ObjenesisSample {

    private static final Logger log = LoggerFactory.getLogger(ObjenesisSample.class);

    /**
     * 使用Objenesis的方法是使用ObjenesisStd(标准)和ObjenesisSerializer(Serializable兼容)。默认情况下，自动确定最佳策略，
     */
    public static void testObjenesisStd() {
        Objenesis objenesis = new ObjenesisStd(false); // 大部分场景我们不需要使用缓存来保留产生的实例
        ObjectSampleBean bean1 = objenesis.newInstance(ObjectSampleBean.class);
        log(bean1);
        ObjectSampleBean bean2 = objenesis.newInstance(ObjectSampleBean.class);
        log(bean2);
    }

    /**
     * 为了提高性能，最好尽可能多地重用ObjectInstantiator对象。例如，如果您正在实例化一个特定类的多个实例，那么从相同的ObjectInstantiator中执行它。
     * 实例InstantiatorStrategy和ObjectInstantiator都可以在多个线程之间共享，并且可以并发使用。它们是线程安全的。
     */
    public static void testObjectInstantiator() {
        Objenesis objenesis = new ObjenesisStd();
        ObjectInstantiator objInstantiator = objenesis.getInstantiatorOf(ObjectSampleBean.class);

        ObjectSampleBean bean1 = (ObjectSampleBean) objInstantiator.newInstance();
        log(bean1);
        ObjectSampleBean bean2 = (ObjectSampleBean) objInstantiator.newInstance();
        log(bean2);
    }

    /**
     * 除了使用标准的初始化策略，我们还可以定制自己的策略
     */
    public static void testObjenesisCustomizedStrategy() {
        Objenesis objenesis = new ObjenesisBase(new InstantiatorStrategy() {
            @Override
            public <T> ObjectInstantiator<T> newInstantiatorOf(Class<T> aClass) {
                return null; // 构建合适的Instantiator
            }
        });
    }

    private static void log(ObjectSampleBean bean) {
        log.info("sample bean, id={}, name={}", bean.getId(), bean.getName());
    }

    public static void main(String[] args) {
        testObjenesisStd();
        testObjectInstantiator();
        testObjenesisCustomizedStrategy();
    }
}
