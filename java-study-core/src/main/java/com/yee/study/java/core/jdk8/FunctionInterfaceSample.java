package com.yee.study.java.core.jdk8;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * JDK8 新特性 - 函数式接口
 * <p>
 * 1. Function {@link FunctionInterfaceSample#functionTest()}
 * 2. BiFunction {@link FunctionInterfaceSample#biFunctionTest()}
 * 3. Consumer {@link FunctionInterfaceSample#consumerTest()}
 * 4. Predicate {@link FunctionInterfaceSample#predicateTest()}
 *
 * @author Roger.Yi
 */
public class FunctionInterfaceSample {

    private static final Logger logger = LoggerFactory.getLogger(FunctionInterfaceSample.class);

    /**
     * Function 示例
     * Function也是一个函数式编程接口；它代表的含义是“函数”，而函数经常是有输入输出的，因此它含有一个apply方法，包含一个输入与一个输出；
     * <p>
     * Function接口中提供的方法详解
     * 1. apply：执行函数，并返回执行结果
     * <p>
     * 2. compose：先执行作为参数的函数，再执行当前函数。
     * <p>
     * 3. andThen：先执行当前函数，在执行作为参数传入的函数
     * <p>
     * 4. identity：返回一个输出跟输入一样的Lambda表达式对象，等价于形如 t -> t 形式的Lambda表达式。
     */
    @Test
    public void functionTest() {
        Function<Integer, Integer> f = s -> ++s; // 函数f: 自增1
        Function<Integer, Integer> g = s -> s * 2; // 函数g: 翻倍

        /**
         * 调用函数f
         */
        Integer result1 = f.apply(1);
        assertEquals(2, result1);

        /**
         * 先执行函数g，再执行函数f，并将函数g的输出作为输入。
         * 相当于以下代码：
         * Integer a = g.apply(1);
         * Integer b = f.apply(a);
         */
        Integer result2 = f.compose(g).apply(3);
        assertEquals(7, result2);

        /**
         * 执行函数f，再执行函数g，并将函数f的输出作为输入；
         * 相当于以下代码
         * Integer a = f.apply(1);
         * Integer b = g.apply(a);
         */
        Integer result3 = f.andThen(g).apply(1);
        assertEquals(4, result3);

        /**
         * identity方法会返回一个不进行任何处理的Function，即输出与输入值相等；
         */
        Integer result4 = (Integer) Function.identity().apply(5);
        assertEquals(5, result4);

        /**
         * 在下面的代码中 str -> str 和 Function.identity() 是没什么区别的因为它们都是 t->t
         */
        Map<String, String> result5 = Arrays.asList("a", "b", "c").stream().collect(Collectors.toMap(Function.identity(), Function.identity()));
        Map<String, String> result6 = Arrays.asList("a", "b", "c").stream().collect(Collectors.toMap(str -> str, str -> str));
        assertEquals(result5, result6);

        /**
         * 下面的代码中 i -> i 和 Function.identity() 不能互相替换，因为 mapToInt需要传入一个 ToIntFunction, 就不能用 Function.identity()
         */
        List<Integer> list = Arrays.asList(1, 2, 3);
        int[] result7 = list.stream().mapToInt(i -> i).toArray();
//        int[] result8 = list.stream().mapToInt(Function.identity()).toArray(); // 编译失败 Function 无法转型为 ToIntFunction
    }

    /**
     * BiFunction 示例
     * BiFunction类，双向接口类，提供了两个输入参数，一个输出参数
     * <p>
     * BiFunction接口中提供的方法详解
     * 1. apply：执行函数
     * <p>
     * 2. andThen：先执行当前函数，在执行作为参数传入的函数
     */
    @Test
    public void biFunctionTest() {
        BiFunction<Integer, Integer, Integer> sum = (a, b) -> a + b; // 函数: 加法
        BiFunction<Integer, Integer, Integer> multiply = (a, b) -> a * b; // 函数: 乘法
        Function<Integer, Integer> negate = a -> -1 * a; // 函数: 取负值

        /**
         * 调用函数 sum 和 multiply
         */
        Integer result1 = sum.apply(2, 3);
        Integer result2 = multiply.apply(2, 3);
        assertEquals(5, result1);
        assertEquals(6, result2);

        /**
         * 先调用sum函数，然后调用negate函数，并将sum的结果作为输入参数
         * 相当于以下代码：
         * Integer a = sum.apply(2, 3);
         * Integer b = negate.apply(a);
         */
        Integer result3 = sum.andThen(negate).apply(2, 3);
        assertEquals(-5, result3);
    }

    /**
     * Consumer 示例
     * <p>
     * Consumer是一个函数式编程接口，它包含有一个有输入而无输出的accept接口方法
     */
    @Test
    public void consumerTest() {
        Consumer prefix = str -> logger.info("prefix:" + str); // 函数prefix: 打印输入字符串，且在字符串加上前缀
        Consumer suffix = str -> logger.info(str + ":suffix"); // 函数prefix: 打印输入字符串，且在字符串加上后缀

        /**
         * 执行一次前缀函数
         */
        prefix.accept("hello");

        /**
         * 连续执行函数，先执行前缀、再执行后缀 最后执行前缀
         */
        prefix.andThen(suffix).andThen(prefix).accept("hi");
    }

    /**
     * Predicate 示例
     * Predicate为函数式接口，predicate的中文意思是“断定”，即判断的意思，判断某个东西是否满足某种条件；
     * Predicate接口中提供的方法详解
     * 1. test：执行函数，根据输入值来做逻辑判断，其结果为True或者False。
     * <p>
     * 2. and：输入为一个 Predicate 函数，与现有函数的结果进行 与 操作
     * <p>
     * 3. or：输入为一个 Predicate 函数，与现有函数的结果进行 或 操作
     * <p>
     * 4. nagate：对现有函数的结果进行 取反 操作
     */
    @Test
    public void predicateTest() {
        Predicate<String> p = o -> o.length() > 3; // 函数p：判断字符串长度是否大于3
        Predicate<String> g = o -> o.startsWith("t"); // 函数g：判断字符串是否以"t"开头

        /**
         * 执行函数p
         */
        boolean result1 = p.test("test");
        assertTrue(result1);

        /**
         * 先执行函数p，再执行negate 对函数p的结果进行取反
         */
        boolean result2 = p.negate().test("test");
        assertFalse(result2);

        /**
         * and: 针对同一输入值，多个Predicate均返回True时返回True，否则返回False；
         */
        boolean result3 = p.and(g).test("hello");
        assertFalse(result3);

        /**
         * or: 针对同一输入值，多个Predicate只要有一个返回True则返回True，否则返回False
         */
        boolean result4 = p.or(g).test("ta");
        assertTrue(result4);
    }

    /**
     * Supplier示例
     * Supplier的接口定义如下：
     *
     * @FunctionalInterface public interface Supplier<T> {
     * T get();
     * }
     * 从接口的定义可以看出，它代表了这样的一类函数：无入参，有一个返回值。
     * <p>
     * Supplier提供了一种包裹代码的能力，被包裹的代码并非实时执行，而是在真正需要使用的时候，被包裹代码段才会被执行，实现延迟计算(懒加载)的效果
     */
    @Test
    public void supplierTest() {
        CacheDateGetter getter = new CacheDateGetter();
        getter.getData(1);
        getter.getData(-1);

        getter.getDataWithOptionalOrElse(1); // 采用 Optional类进行判空处理
        getter.getDataWithOptionalOrElse(-1); // 采用 Optional类进行判空处理

        getter.getDataWithOptionalOrElseGet(1); // 采用 Optional类进行判空处理
        getter.getDataWithOptionalOrElseGet(-1); // 采用 Optional类进行判空处理
    }

    static class CacheDateGetter {

        private String getFromCache(int id) {
            System.out.println("getFromCache id=" + id);
            return id > 0 ? "cacheData" : null;
        }

        private String getFromDb(int id) {
            System.out.println("getFromDb id=" + id);
            return "dbData";
        }

        public String getData(int id) {
            String data = getFromCache(id);
            return data == null ? getFromDb(id) : data;
        }

        /**
         * 使用 orElse 的方法，会导致每次调用无论 getFromCache方法的结果是否为null，都会调用一次 getFromDb方法
         */
        public String getDataWithOptionalOrElse(int id) {
            return Optional.ofNullable(getFromCache(id)).orElse(getFromDb(id));
        }

        /**
         * 使用 orElseGet 方法会将函数传入，只有当 getFromCache方法的结果为null时，才会调用 getFromDb 方法
         */
        public String getDataWithOptionalOrElseGet(int id) {
            return Optional.ofNullable(getFromCache(id)).orElseGet(() -> getFromDb(id));
        }
    }
}
