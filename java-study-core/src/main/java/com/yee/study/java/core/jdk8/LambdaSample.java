package com.yee.study.java.core.jdk8;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.*;

/**
 * JDK8 新特性 - Lambda表达式
 * <p>
 * <p>
 * 关于lambda的注意:
 * <p>
 * 1）lambda表达式仅能放入如下代码：
 * 预定义使用了 @Functional 注释的函数式接口，自带一个抽象函数的方法，或者SAM（Single Abstract Method 单个抽象方法）类型。这些称为lambda表达式的目标类型，可以用作返回类型，或lambda目标代码的参数。
 * 例如，若一个方法接收Runnable、Comparable或者 Callable 接口，都有单个抽象方法，可以传入lambda表达式。
 * 类似的，如果一个方法接受声明于 java.util.function 包内的接口，例如 Predicate、Function、Consumer 或 Supplier，那么可以向其传lambda表达式。
 * <p>
 * 2）lambda表达式内可以使用方法引用，仅当该方法不修改lambda表达式提供的参数。本例中的lambda表达式可以换为方法引用，因为这仅是一个参数相同的简单方法调用。
 * list.forEach(n -> System.out.println(n));
 * list.forEach(System.out::println);  // 使用方法引用
 * <p>
 * 然而，若对参数有任何修改，则不能使用方法引用，而需键入完整地lambda表达式，如下所示：
 * list.forEach((String s) -> System.out.println("*" + s + "*"));
 * 事实上，可以省略这里的lambda参数的类型声明，编译器可以从列表的类属性推测出来。
 * <p>
 * 3）lambda内部可以使用静态、非静态和局部变量，这称为lambda内的变量捕获。
 * <p>
 * 4）Lambda表达式在Java中又称为闭包或匿名函数，所以如果有同事把它叫闭包的时候，不用惊讶。
 * <p>
 * 5）Lambda方法在编译器内部被翻译成私有方法，并派发 invokedynamic 字节码指令来进行调用。可以使用JDK中的 javap 工具来反编译class文件。使用 javap -p 或 javap -c -v 命令来看一看lambda表达式生成的字节码。大致应该长这样：
 * private static java.lang.Object lambda$0(java.lang.String);
 * <p>
 * 6）lambda表达式有个限制，那就是只能引用 final 或 final 局部变量，这就是说不能在lambda内部修改定义在域外的变量。
 * Compile time error : "local variables referenced from a lambda expression must be final or effectively final"
 * 另外，只是访问它而不作修改是可以的，如下所示：
 * List<Integer> primes = Arrays.asList(new Integer[]{2, 3,5,7});
 * int factor = 2;
 * primes.forEach(element -> { System.out.println(factor*element); });
 * 因此，它看起来更像不可变闭包，类似于Python。
 * <p>
 * 7) 在lambda中，this不是指向lambda表达式产生的那个SAM对象，而是声明它的外部对象。
 *
 * @author Roger.Yi
 */
public class LambdaSample {

    /**
     * Java8中引入了一个新的操作符，"->"，该操作符称为箭头操作符或者Lambda操作符，箭头操作符将Lambda表达式拆分成两部分；
     * 左侧: Lambda表达式的参数列表，对应的是接口中抽象方法的参数列表；
     * 右侧: Lambda表达式中所需要执行的功能(Lambda体)，对应的是对抽象方法的实现；(函数式接口(只能有一个抽象方法))
     * Lambda表达式的实质是　对接口的实现；
     */
    @Test
    public void anonymousInnerClass() {
        /**
         * 1. 接口中的抽象方法 : 无参数，无返回值；
         *
         * 以下写法的效果是一样的：
         * new Runnable() {
         *     @Override
         *     public void run() {
         *          System.out.println("In Java8!")
         *     }
         * }
         */
        new Thread(() -> System.out.println("In Java8!")).start();

        /**
         * 2. 接口中的抽象方法 : 一个参数且无返回值； (若只有一个参数，那么小括号可以省略不写)
         *
         * x -> System.out.println("hello") 与 (x) -> System.out.println("hello") 一样
         */
        InterfaceOne one = x -> System.out.println("hello");

        /**
         * 3. 两个参数，有返回值，并且有多条语句 ：　要用大括号括起来，而且要写上return
         *
         * Lambda表达式的参数列表的数据类型 可以省略不写，因为JVM编译器通过上下文推断出数据类型，即"类型推断"，
         * (Integer x,Integer y ) -> Integer.compare(x,y)可以简写成(x,y) -> Integer.compare(x,y)；
         *
         * 以下写法的效果是一样的：
         * Comparator<Integer> compartor = new Comparator<Integer>() {
         *     @Override
         *     public int compare(Integer x, Integer y) {
         *         System.out.println(x + y);
         *         return Integer.compare(y, x);
         *     }
         * };
         */
        Comparator<Integer> compartorOne = (x, y) -> {
            System.out.println(x + y);
            return Integer.compare(y, x);
        };

        /**
         * 4. 两个参数，有返回值，只有一条语句 ：　大括号省略，return省略
         *
         * 以下写法的效果是一样的：
         * Comparator<Integer> compartor = new Comparator<Integer>() {
         *     @Override
         *     public int compare(Integer x, Integer y) {
         *         return Integer.compare(y, x);
         *     }
         * };
         */
        Comparator<Integer> compartorTwo = (x, y) -> Integer.compare(y, x);
    }

    interface InterfaceOne {
        void doSomething(String input);
    }

    /**
     * 集合遍历
     */
    @Test
    public void iterateCollection() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        list.forEach(System.out::print);
        list.forEach(e -> System.out.print(e)); // 如果没有对集合元素引用或者修改，可以简化成 System.out::print
    }

    /**
     * Stream函数式操作流元素集合
     */
    @Test
    public void stream() {
        List<Integer> nums = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        System.out.println("求和：" + nums.stream()//转成Stream
                .filter(Objects::nonNull)//过滤
                .distinct()//去重
                .mapToInt(num -> num * 2)//map操作
                .skip(2)//跳过前2个元素
                .limit(4)//限制取前4个元素
                .peek(System.out::println)//流式处理对象函数
                .sum());//求和
    }

    /**
     * 方法引用, 与Lambda表达式联合使用
     * <p>
     * Lambda体中调用方法的参数列表和返回值类型，要和函数式接口中抽象方法的参数列表和返回值类型保持一致
     */
    @Test
    public void methodReference() {
        /**
         * 1. 对象::实例方法名
         *
         * 以下2中写法和用方法引用的效果是一样的。
         *
         * a) Consumer<String> consumerOne = s -> System.out.println(s);
         *    useConsumer(consumerOne,"123");
         * b) useConsumer(s -> System.out.println(s),"123");
         */
        useConsumer(System.out::println, "123"); //因为println和 accept 是同样的只有一个入参，没有返回值

        /**
         * 与 (s, index) -> s.charAt(index) 一样，第一个参数可以省略，但是必须是 String类型
         */
        BiFunction<String, Integer, Character> bf = String::charAt; //这里第一个必须传入　String

        /**
         * 2. 类名::静态方法
         *
         * 与 Comparator<Integer> com = (x,y) -> Integer.compare(x,y) 一样
         */
        Comparator<Integer> comparator = Integer::compare;

        /**
         * 3. 类::实例方法名
         *
         * 若Lambda参数列表中的第一个参数是实例方法的第一个调用者，而第二个参数是实例方法的参数时，可以使用ClassName :: method
         */
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        BiPredicate<String, String> bp2 = String::equals;

        /**
         * 构造器引用。语法是Class::new，或者更一般的Class< T >::new，要求构造器方法是没有参数
         *
         * 需要调用构造器的参数列表，要与函数式接口中的抽象方法的参数列表保持一致；
         */
        Supplier<Car> carSupplier = Car::new; // 无参调用l
        Car carOne = carSupplier.get();

        Function<String, Car> carFunction = Car::new; // 带参数调用
        Car carTwo = carFunction.apply("Auto");
    }

    public static <T> void useConsumer(Consumer<T> consumer, T t) {
        consumer.accept(t);
    }

    public static class Car {

        public Car() {
        }

        public Car(String name) {
        }
    }
}
