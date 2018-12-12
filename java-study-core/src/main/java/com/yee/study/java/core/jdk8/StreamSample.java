package com.yee.study.java.core.jdk8;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * JDK8 新特性 - Stream
 * <p>
 * Stream对象提供多个非常有用的方法，这些方法可以分成两类：
 * 中间操作(intermediate)：将原始的Stream转换成另外一个Stream；如filter返回的是过滤后的Stream。
 * 终端操作(terminal)：产生的是一个结果或者其它的复合操作；如count或者forEach操作。
 * 原Stream对象经过终端或中间操作后，无法再次使用（A stream should be operated on (invoking an intermediate or terminal stream
 * operation) only once.）。
 * <p>
 * 中间操作(intermediate)如下：
 * sequential	返回一个相等的串行的Stream对象，如果原Stream对象已经是串行就可能会返回原对象
 * parallel	返回一个相等的并行的Stream对象，如果原Stream对象已经是并行的就会返回原对象
 * unordered	返回一个不关心顺序的Stream对象，如果原对象已经是这类型的对象就会返回原对象
 * onClose	返回一个相等的Steam对象，同时新的Stream对象在执行Close方法时会调用传入的Runnable对象
 * close	关闭Stream对象
 * filter	元素过滤：对Stream对象按指定的Predicate进行过滤，返回的Stream对象中仅包含未被过滤的元素
 * map	元素一对一转换：使用传入的Function对象对Stream中的所有元素进行处理，返回的Stream对象中的元素为原元素处理后的结果
 * mapToInt	元素一对一转换：将原Stream中的使用传入的IntFunction加工后返回一个IntStream对象
 * flatMap	元素一对多转换：对原Stream中的所有元素进行操作，每个元素会有一个或者多个结果，然后将返回的所有元素组合成一个统一的Stream并返回；
 * distinct	去重：返回一个去重后的Stream对象
 * sorted	排序：返回排序后的Stream对象
 * peek	使用传入的Consumer对象对所有元素进行消费后，返回一个新的包含所有原来元素的Stream对象
 * limit	获取有限个元素组成新的Stream对象返回
 * skip	抛弃前指定个元素后使用剩下的元素组成新的Stream返回
 * <p>
 * 终端操作(terminal)如下：
 * iterator	返回Stream中所有对象的迭代器;
 * spliterator	返回对所有对象进行的spliterator对象
 * forEach	对所有元素进行迭代处理，无返回值
 * forEachOrdered	按Stream的Encounter所决定的序列进行迭代处理，无返回值
 * toArray	返回所有元素的数组
 * reduce	使用一个初始化的值，与Stream中的元素一一做传入的二合运算后返回最终的值。每与一个元素做运算后的结果，再与下一个元素做运算。它不保证会按序列执行整个过程。
 * collect	根据传入参数做相关汇聚计算
 * min	返回所有元素中最小值的Optional对象；如果Stream中无任何元素，那么返回的Optional对象为Empty
 * max	与Min相反
 * count	所有元素个数
 * anyMatch	只要其中有一个元素满足传入的Predicate时返回True，否则返回False
 * allMatch	所有元素均满足传入的Predicate时返回True，否则False
 * noneMatch	所有元素均不满足传入的Predicate时返回True，否则False
 * findFirst	返回第一个元素的Optioanl对象；如果无元素返回的是空的Optional； 如果Stream是无序的，那么任何元素都可能被返回。
 * findAny	返回任意一个元素的Optional对象，如果无元素返回的是空的Optioanl。
 * isParallel	判断是否当前Stream对象是并行的
 *
 * @author Roger.Yi
 */
public class StreamSample {

    private static final Logger logger = LoggerFactory.getLogger(StreamSample.class);

    /**
     * 构建stream示例
     */
    @Test
    public void buildStream() {
        // 1. 单个值
        Stream<String> stream = Stream.of("a", "b", "c");

        // 2. 数组
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);

        // 3. 集合
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();

        // 对于基本数值型，目前有三种对应的包装类型 Stream： IntStream、LongStream、DoubleStream
        // 4. 数值流的构造
        IntStream.of(new int[]{1, 2, 3}).forEach(x -> logger.info(String.valueOf(x)));
        IntStream.range(1, 4).forEach(x -> logger.info(String.valueOf(x)));

        // 5. 流转换为其它数据结构
        // 数组
        stream = Stream.of("e", "f", "g");
        String[] strArray1 = stream.toArray(String[]::new);

        // 集合
        stream = Stream.of("e", "f", "g");
        List<String> list1 = stream.collect(Collectors.toList());

        stream = Stream.of("e", "f", "g");
        List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));

        stream = Stream.of("e", "f", "g", "g");
        Set set1 = stream.collect(Collectors.toSet());

        stream = Stream.of("e", "f", "g");
        Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));

        // 字符串
        stream = Stream.of("e", "f", "g");
        String str = stream.collect(Collectors.joining()).toString();
    }

    /**
     * generator方法：生成一个无限长度的Stream，其元素的生成是通过给定的Supplier（这个接口可以看成一个对象的工厂，每次调用返回一个给定类型的对象）
     */
    @Test
    public void generate() {
        // 以下三个方法是相同的功能，都是用于随机数的生成
        Stream.generate(new Supplier<Double>() {
            @Override
            public Double get() {
                return Math.random();
            }
        });
        Stream.generate(() -> Math.random());
        Stream.generate(Math::random);
    }

    /**
     * map/flatMap 示例
     */
    @Test
    public void map() {
        // map 生成的是个 1:1 映射，每个输入元素，都按照规则转换成为另外一个元素
        // 转换大写
        List<String> wordList = Arrays.asList("beijing", "shanghai", "macau");
        List<String> output = wordList.stream().
                map(String::toUpperCase).
                collect(Collectors.toList());

        // 平方数
        List<Integer> nums = Arrays.asList(1, 2, 3, 4);
        List<Integer> squareNums = nums.stream().
                map(n -> n * n).
                collect(Collectors.toList());

        // flatMap 用于处理一对多映射关系
        Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
        Stream<Integer> outputStream = inputStream.
                flatMap((childList) -> childList.stream());
        nums = outputStream.collect(Collectors.toList());
        logger.info("end");
    }

    /**
     * filter 示例
     */
    @Test
    public void filter() {
        // 留下偶数
        Integer[] evens = Stream.of(1, 2, 3, 4, 5, 6).filter(n -> n % 2 == 0).toArray(Integer[]::new);

        // 把单词挑出来
        List<String> output = Stream.of("This is Shanghai", "Shanghai is a big city", "Beijing is the capital").
                flatMap(line -> Stream.of(line.split(" "))).
                filter(word -> word.length() > 2). // 排除长度小于等于2的字符串 
                collect(Collectors.toList());

        logger.info("end");
    }

    /**
     * reduce 的主要作用是把 Stream 元素组合起来。
     * 它提供一个起始值（种子），然后依照运算规则（BinaryOperator），和前面 Stream 的第一个、第二个、第 n 个元素组合。
     * 从这个意义上说，字符串拼接、数值的 sum、min、max、average 都是特殊的 reduce。例如 Stream 的 sum 就相当于
     * Integer sum = integers.reduce(0, (a, b) -> a+b); 或
     * Integer sum = integers.reduce(0, Integer::sum);
     * <p>
     * 也有没有起始值的情况，这时会把 Stream 的前面两个元素组合起来，返回的是 Optional。
     */
    @Test
    public void reduce() {

        // reduce方法有三个变种，输入参数分别是一个参数、二个参数以及三个参数；

        /**
         * 1. 一个参数的reduce：Optional<T> reduce(BinaryOperator<T> accumulator);
         *
         * 假设Stream中的元素a[0]、a[1]、a[2] ... a[n - 1]，它表达的计算含义，使用Java代码来表述如下：
         * T result = a[0];
         * for (int i = 1; i < n; i++) {
         *     result = accumulator.apply(result, a[i]);
         * }
         * return result;
         *
         * 也就是说，a[0]与a[1]进行二合运算，结果与a[2]做二合运算，一直到最后与a[n-1]做二合运算。
         *
         * 可以很方便的完成求和、求最大最小值
         */
        // 求和的三个不同的写法，sumValue1 = sumValue2 = sumValue3 = 10, 无起始值
        Integer sumValue1 = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
        Integer sumValue2 = Stream.of(1, 2, 3, 4).reduce((a, b) -> a + b).get();
        Integer sumValue3 = Stream.of(1, 2, 3, 4).reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        }).get();

        // 求最大值
        Integer maxValue1 = Stream.of(8, 2, 9, 1).reduce(Integer::max).get();
        Integer maxValue2 = Stream.of(8, 2, 9, 1).reduce((a, b) -> a >= b ? a : b).get();
        Integer maxValue3 = Stream.of(8, 2, 9, 1).reduce(new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer >= integer2 ? integer : integer2;
            }
        }).get();

        /**
         * 2. 两个参数的reduce：T reduce(T identity, BinaryOperator<T> accumulator);
         *
         * 它多了一个T类型的参数；实际上就相当于需要计算的值在Stream的基础上多了一个初始化的值。
         * 同理，当对n个元素的数组进行运算时，其表达的含义如下：
         *
         * T result = identity;
         * for (int i = 0; i < n; i++) {
         *     result = accumulator.apply(result, a[i]);
         * }
         * return result;
         *
         * 注意区分与一个参数的Reduce方法的不同：它多了一个初始化的值，因此计算的顺序是identity与a[0]进行二合运算，结果与a[1]再进行二合运算，最终与a[n-1]进行二合运算。
         * 因此它与一参数时的应用场景类似，不同点是它使用在可能需要某些初始化值的场景中。
         */
        // 字符串连接，concat = "Value=ABCD", 相当于 "Value=" + "A" + "B" + "C" + "D"
        String concat = Stream.of("A", "B", "C", "D").reduce("Value=", String::concat);
        // 求和，sumValue = 11, 有起始值1, 相当于 1 + 1 + 2 + 3 + 4
        int sumValue4 = Stream.of(1, 2, 3, 4).reduce(1, Integer::sum);

        /**
         * 3. 三个参数的reduce：
         * <U> U reduce(U identity,
         *              BiFunction<U, ? super T, U> accumulator,
         *              BinaryOperator<U> combiner);
         *
         * 分析下它的三个参数：
         * 1) identity: 一个初始化的值；这个初始化的值其类型是泛型U，与Reduce方法返回的类型一致；注意此时Stream中元素的类型是T，与U可以不一样也可以一样；
         *              不管Stream中存储的元素是什么类型，U都可以是任何类型，如U可以是一些基本数据类型的包装类型Integer、Long等；或者是String，又或者是一些集合类型ArrayList等
         * 2) accumulator: 其类型是BiFunction，输入是U与T两个类型的数据，而返回的是U类型；也就是说返回的类型与输入的第一个参数类型是一样的，而输入的第二个参数类型与Stream中元素类型是一样的。
         * 3) combiner: 其类型是BinaryOperator，支持的是对U类型的对象进行操作；
         * 第三个参数combiner主要是使用在并行计算的场景下；如果Stream是非并行时，第三个参数实际上是不生效的。
         * 因此针对这个方法的分析需要分并行与非并行两个场景。
         *
         * 3.1 非并行
         * 如果Stream是非并行的，combiner不生效；其计算过程与两个参数时的Reduce基本是一致的。
         * 如Stream中包含了N个元素，其计算过程使用Java代码表述如下：
         * U result = identity;
         * for (T element：this stream) {
         *     result = accumulator.apply(result, element);
         * }
         * return result;
         */

        /**
         * 以下是将Stream中的元素添加到一个ArrayList中，此处是非并行场景，所以第三个参数 (r1, r2) -> r1 其实没有什么实际含义，返回r1或r2都可以
         */
        List<String> strList1 = Stream.of("aa", "ab", "c", "ad").
                reduce(new ArrayList<String>(), (r, t) -> {
                    r.add(t);
                    return r;
                }, (r1, r2) -> r1);
        List<String> strList2 = Stream.of("aa", "ab", "c", "ad").
                reduce(new ArrayList<String>(), (r, t) -> {
                    r.add(t);
                    return r;
                }, (r1, r2) -> r2);
        List<String> strList3 = Stream.of("aa", "ab", "c", "ad")
                .reduce(new ArrayList<String>(), new BiFunction<ArrayList<String>, String, ArrayList<String>>() {
                    @Override
                    public ArrayList<String> apply(ArrayList<String> u, String s) {
                        u.add(s);
                        return u;
                    }
                }, new BinaryOperator<ArrayList<String>>() {
                    @Override
                    public ArrayList<String> apply(ArrayList<String> strings, ArrayList<String> strings2) {
                        return strings;
                    }
                });

        /**
         * 3.2 并行
         * 当Stream是并行时，第三个参数就有意义了，它会将不同线程计算的结果调用combiner做汇总后返回。
         * 注意由于采用了并行计算，前两个参数与非并行时也有了差异！
         *
         * 举个简单点的例子，计算4+1+2+3的结果，其中4是初始值，如下计算结果是18而不是10，为什么会这样呢？
         * 计算过程现在是这样的：线程1：1 + 4 = 5；线程2：2 + 4 = 6；线程3：3 + 4 = 7；Combiner函数： 5 + 6 + 7 = 18！
         * 初始化值4（第一个参数）会参会与每次线程的计算
         */
        Integer sumValue5 = Stream.of(1, 2, 3).parallel().reduce(4, (s1, s2) -> s1 + s2, (s1, s2) -> s1 + s2);

        Stream<String> s1 = Stream.of("aa", "ab", "c", "ad");

        //模拟Filter查找其中含有字母a的所有元素，由于使用了r1.addAll(r2)，其打印结果将不会是预期的aa ab ad
        //Predicate<String> predicate = t -> t.contains("a");
        List<String> strList4 = Stream.of("a", "b").parallel().reduce(new ArrayList<String>(), (r, t) -> { r.add(t);  return r; },
                (r1, r2) -> {r1.addAll(r2); return r1; });

        logger.info("end");
    }
}
