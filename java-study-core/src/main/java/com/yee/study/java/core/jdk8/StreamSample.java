package com.yee.study.java.core.jdk8;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
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
     * 构建 Stream 流的四种方式 示例
     */
    @Test
    public void buildStream() {
        /**
         * 1. 通过 Collection 的 stream() 构建
         */
        List<String> list = Arrays.asList("1", "2", "3", "4", "0", "222", "33");
        Stream<String> stream1 = list.stream();
        Stream<String> stream2 = list.parallelStream();

        /**
         * 2. 通过 Arrays 的 stream() 方法构建
         */
        Stream<String> stream3 = Arrays.stream(new String[]{"a", "b", "c"});

        /**
         * 3. 通过Stream类中得 of（）静态方法构建
         */
        Stream<String> stream4 = Stream.of("a", "b", "c");

        /**
         * 对于基本数值型，目前有三种对应的包装类型 Stream： IntStream、LongStream、DoubleStream
         */
        IntStream.of(new int[]{1, 2, 3}).forEach(x -> logger.info(String.valueOf(x)));
        IntStream.range(1, 4).forEach(x -> logger.info(String.valueOf(x)));

        /**
         * 4 创建无限流(迭代、生成)
         */
        Stream<Integer> stream5 = Stream.iterate(2, (x) -> x * 2);
        Stream<Double> stream6 = Stream.generate(() -> Math.random());
    }

    /**
     * map/flatMap 示例
     * <p>
     * map - 接受lambda 将元素转换为其他形式或提取信息。接受一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新元素
     * <p>
     * flatMap 接受一个函数作为参数，将流中的每个值都转成另一个流，然后把所有流连成一个流。
     */
    @Test
    public void map() {
        /**
         * map 生成的是个 1:1 映射，每个输入元素，都按照规则转换成为另外一个元素
         */
        Stream.of("beijing", "shanghai", "macau").map(String::toUpperCase).forEach(System.out::println); // 转换大写
        Stream.of(1, 2, 3, 4).map(n -> n * n).forEach(System.out::println); // 平方数

        /**
         * flatMap 用于处理一对多映射关系
         */
        Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));
        inputStream.flatMap((childList) -> childList.stream()).forEach(System.out::println);
    }

    /**
     * filter 示例
     */
    @Test
    public void filter() {
        /**
         * 留下偶数
         */
        Stream.of(1, 2, 3, 4, 5, 6).filter(n -> n % 2 == 0).forEach(System.out::println);

        /**
         * 把单词挑出来，且排除长度小于等于2的字符串
         */
        Stream.of("This is Shanghai", "Shanghai is a big city", "Beijing is the capital").
                flatMap(line -> Stream.of(line.split(" "))).
                filter(word -> word.length() > 2).
                forEach(System.out::println);
    }

    /**
     * distinct 示例
     */
    @Test
    public void distinct() {
        Stream.of(1, 2, 3, 4, 5, 6, 3, 1).distinct().forEach(System.out::println);
    }

    /**
     * limit 和 skip 示例
     */
    @Test
    public void limitAndSkip() {
        /**
         * 输出 3, 4, 5
         */
        Stream.of(1, 2, 3, 4, 5, 6).skip(2).limit(3).forEach(System.out::println);
    }

    /**
     * sort 示例
     */
    @Test
    public void sort() {
        /**
         * 自然排序，输出 1, 2, 3, 4, 5, 6
         */
        Stream.of(4, 5, 2, 6, 3, 1).sorted().forEach(System.out::println);

        /**
         * 自定义排序，输出 6, 5, 4, 3, 2, 1
         */
        Stream.of(4, 5, 2, 6, 3, 1).sorted((x, y) -> y - x).forEach(System.out::println);
    }

    /**
     * peek 示例
     * peek 方法主要用于 debug
     */
    @Test
    public void peek() {
        /**
         * 输出 1, 2, 3, -4, 5, -6
         */
        Stream.of(1, 2, 3, -4, 5, -6).peek(a -> System.out.println(a * -1)).forEach(System.out::println);
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
        List<String> strList4 = Stream.of("a", "b").parallel().reduce(new ArrayList<String>(), (r, t) -> {
                    r.add(t);
                    return r;
                },
                (r1, r2) -> {
                    r1.addAll(r2);
                    return r1;
                });

        logger.info("end");
    }

    /**
     * count 示例
     */
    @Test
    public void count() {
        long count = Stream.of("apple", "banana", "orange", "waltermaleon", "grape").count();
        Assert.assertEquals(5, count);
    }

    /**
     * min 和 max 示例
     */
    @Test
    public void minAndMax() {
        Stream.of(1, 2, 3, 4).min(Integer::compareTo).ifPresent(System.out::println);
        Stream.of(1, 2, 3, 4).max(Integer::compareTo).ifPresent(System.out::println);
    }

    /**
     * find 示例
     */
    @Test
    public void find() {
        Stream.of("apple", "banana", "orange", "waltermaleon", "grape").findFirst().ifPresent(System.out::println);
        Stream.of("apple", "banana", "orange", "waltermaleon", "grape").findAny().ifPresent(System.out::println);
    }

    /**
     * match 示例
     */
    @Test
    public void match() {
        boolean match1 = Stream.of(1, 2, 3, 4, 5).allMatch(x -> x > 0);
        Assert.assertTrue(match1);

        boolean match2 = Stream.of(1, 2, 3, 4, 5).noneMatch(x -> x < 0);
        Assert.assertTrue(match2);

        boolean match3 = Stream.of(1, 2, 3, 4, 5).anyMatch(x -> x > 6);
        Assert.assertFalse(match3);
    }

    /**
     * collect 示例
     */
    @Test
    public void collect() {
        /**
         * 1. toList
         */
        Stream.of("I", "love", "you", "too", "you").collect(Collectors.toList()).forEach(System.out::println);

        /**
         * 2. toSet
         */
        Stream.of("I", "love", "you", "too", "you").collect(Collectors.toSet()).forEach(System.out::println);

        /**
         * 3. toCollection 通过指定集合类型来生成集合
         */
        Stream.of("I", "love", "you", "too", "you").collect(Collectors.toCollection(ArrayList::new)).forEach(System.out::println);
        Stream.of("I", "love", "you", "too", "you").collect(Collectors.toCollection(HashSet::new)).forEach(System.out::println);

        /**
         * 3. toMap
         */
        Stream.of("I", "love", "you", "too").collect(Collectors.toMap(Function.identity(), String::length)).forEach((x, y) -> System.out.println(x + ", " + y));

        /**
         * 4. joining
         */
        String joining = Stream.of("I", "love", "you").collect(Collectors.joining(" "));
        Assert.assertEquals("I love you", joining);

        /**
         * 5. counting, maxBy, minBy 此处建议直接使用 Stream 的 count, max, min 替换
         */
        Stream.of(1, 0, -10, 9, 8, 100, 200, -80).collect(Collectors.counting());
        Stream.of(1, 0, -10, 9, 8, 100, 200, -80).collect(Collectors.minBy(Integer::compareTo));
        Stream.of(1, 0, -10, 9, 8, 100, 200, -80).collect(Collectors.maxBy(Integer::compareTo));

        /**
         * 6. summingInt()、summarizingLong()、summarizingDouble() 这三个分别用于int、long、double类型数据一个求总操作，返回的是一个SummaryStatistics(求总)。
         *    包含了数量统计count、求和sum、最小值min、平均值average、最大值max
         */
        double average = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).collect(Collectors.summarizingInt(Integer::valueOf)).getAverage();
        double average2 = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).collect(Collectors.averagingDouble(Integer::valueOf)).doubleValue();
        Assert.assertTrue(average == average2);

        /**
         * 7. groupingBy
         *    groupingByConcurrent() 和 groupingBy，这两者区别也仅是多线程和单线程的使用场景
         */
        // 默认结果类型为 Map<String,List<Integer>>
        Stream.of(-6, -7, -8, -9, 1, 2, 3, 4, 5, 6, 0)
                .collect(Collectors.groupingBy(n -> n == 0 ? "等于" : n > 0 ? "大于" : "小于")).forEach((x, y) -> System.out.println(x + ":" + y));

        // 自定义下游收集器 Map<String,Set<Integer>>
        Stream.of(-6, -7, -8, -9, 1, 2, 3, 4, 5, 6, 0)
                .collect(Collectors.groupingBy(n -> n == 0 ? "等于" : n > 0 ? "大于" : "小于", Collectors.toSet()));

        // 自定义map容器 和 下游收集器 Map<String,Set<Integer>>
        Stream.of(-6, -7, -8, -9, 1, 2, 3, 4, 5, 6, 0)
                .collect(Collectors.groupingBy(n -> n == 0 ? "等于" : n > 0 ? "大于" : "小于", LinkedHashMap::new, Collectors.toSet()));
    }
}
