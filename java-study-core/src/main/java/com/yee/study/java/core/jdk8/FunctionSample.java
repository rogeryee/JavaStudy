package com.yee.study.java.core.jdk8;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * JDK8 新特性 - 函数式编程
 * <p>
 * 1. Consumer
 * 2. Function
 * 3. Predicate
 * 4. Stream {@link StreamSample}
 * 5. Optional {@link OptionalSample}
 *
 * @author Roger.Yi
 */
public class FunctionSample {

    private static final Logger logger = LoggerFactory.getLogger(FunctionSample.class);

    /**
     * Consumer 示例
     * <p>
     * Consumer是一个函数式编程接口，它包含有一个有输入而无输出的accept接口方法
     */
    @Test
    public void consumerTest() {
        Consumer f = System.out::println; // 函数f: 打印输入字符串
        Consumer f2 = n -> System.out.println(n + "-F2"); // 函数f2: 将字符串拼接"-F2"并打印

        // 执行一次函数f
        f.accept("test1");

        // 执行f函数，再执行分函数f2
        f.andThen(f2).accept("test2");

        //连续执行函数f
        f.andThen(f).andThen(f).andThen(f).accept("test3");

        logger.info("end");
    }

    /**
     * Function 示例
     * Function也是一个函数式编程接口；它代表的含义是“函数”，而函数经常是有输入输出的，因此它含有一个apply方法，包含一个输入与一个输出；
     */
    @Test
    public void functionTest() {
        Function<Integer, Integer> f = s -> ++s; // 函数f: 自增1
        Function<Integer, Integer> g = s -> s * 2; // 函数g: 翻倍

        /**
         * 调用函数f
         */
        System.out.println(f.apply(1));

        /**
         * 先执行函数g，在执行函数f并将函数g的输出作为输入。
         * 相当于以下代码：
         * Integer a = g.apply(1);
         * System.out.println(f.apply(a));
         */
        System.out.println(f.compose(g).apply(3));

        /**
         * 执行函数f，再执行函数g并将函数f的输出作为输入；
         * 相当于以下代码
         * Integer a = f.apply(1);
         * System.out.println(g.apply(a));
         */
        System.out.println(f.andThen(g).apply(1));

        /**
         * identity方法会返回一个不进行任何处理的Function，即输出与输入值相等；
         */
        System.out.println(Function.identity().apply("a"));
    }

    /**
     * Predicate 示例
     * Predicate为函数式接口，predicate的中文意思是“断定”，即判断的意思，判断某个东西是否满足某种条件；
     * 因此它包含test方法，根据输入值来做逻辑判断，其结果为True或者False。
     */
    @Test
    public void predicateTest() {
        Predicate<String> p = o -> o.equals("test"); // 函数p：判断字符串是否等于"test"
        Predicate<String> g = o -> o.startsWith("t"); // 函数g：判断字符串是否以"t"开头

        /**
         * negate: 用于对原来的Predicate做取反处理；
         * 如当调用p.test("test")为True时，调用p.negate().test("test")就会是False；
         */
        Assert.assertFalse(p.negate().test("test"));

        /**
         * and: 针对同一输入值，多个Predicate均返回True时返回True，否则返回False；
         */
        Assert.assertTrue(p.and(g).test("test"));

        /**
         * or: 针对同一输入值，多个Predicate只要有一个返回True则返回True，否则返回False
         */
        Assert.assertTrue(p.or(g).test("ta"));
    }
}
