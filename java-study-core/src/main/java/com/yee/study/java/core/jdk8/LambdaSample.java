package com.yee.study.java.core.jdk8;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

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
     * 集合遍历
     */
    @Test
    public void iterateCollection() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

        list.forEach(System.out::print);
        System.out.println("----------------");

        list.forEach(e -> System.out.print(e));
        System.out.println();
    }

    /**
     * 利用函数式接口实现匿名内部类
     */
    @Test
    public void anonymousInnerClass() {
        new Thread(() -> System.out.println("In Java8!")).start();
    }

    /**
     * Predicate是jdk8 中的新增接口, 共有5个方法,
     * <p>
     * Returns a predicate which evaluates to true only if this predicate
     * and the provided predicate both evaluate to true.
     * and(Predicate<? super T> p)
     * <p>
     * Returns a predicate which negates the result of this predicate.
     * negate()
     * <p>
     * Returns a predicate which evaluates to true if either
     * this predicate or the provided predicate evaluates to true
     * or(Predicate<? super T> p)
     * <p>
     * Returns a predicate that evaluates to true if both or neither
     * of the component predicates evaluate to true
     * xor(Predicate<? super T> p)
     * <p>
     * Returns true if the input object matches some criteria
     * test(T t)
     * <p>
     */
    @Test
    public void predicate() {
        List<String> languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp", "Swift");

        Predicate<String> startWithS = (n) -> n.startsWith("S"); // 以J开头的字符串
        Predicate<String> endWithA = (n) -> n.endsWith("a"); // 以a结尾的字符串
        Predicate<String> fourLength = (n) -> n.length() == 5; // 长度等于5的字符串
        Predicate<String> fourGtLength = (n) -> n.length() > 4; // 长度大于4的字符串

        System.out.println("startWithS : --------------");
        filterWithTest(languages, startWithS); // 以J开头的字符串

        System.out.println("endWithA : --------------");
        filterWithTest(languages, endWithA); // 以a结尾的字符串

        System.out.println("fourGtLength : --------------");
        filterWithTest(languages, fourGtLength); // 长度大于4的字符串

        System.out.println("All : --------------");
        filterWithTest(languages, (str) -> true); // 打印所有

        System.out.println("No : --------------");
        filterWithTest(languages, (str) -> false); // 不打印所有

        System.out.println("fourGtLength and startWithS : --------------");
        filterWithTest(languages, fourGtLength.and(startWithS));
    }

    public static void filterWithTest(List<String> names, Predicate condition) {
        for (String name : names) {
            if (condition.test(name)) {
                System.out.println(name + " ");
            }
        }
    }

    /**
     * Stream函数式操作流元素集合
     */
    @Test
    public void stream() {
        List<Integer> nums = Lists.newArrayList(1, 1, null, 2, 3, 4, null, 5, 6, 7, 8, 9, 10);
        System.out.println("求和：" + nums.stream()//转成Stream
                .filter(team -> team != null)//过滤
                .distinct()//去重
                .mapToInt(num -> num * 2)//map操作
                .skip(2)//跳过前2个元素
                .limit(4)//限制取前4个元素
                .peek(System.out::println)//流式处理对象函数
                .sum());//求和
    }

    /**
     * 方法引用, 与Lambda表达式联合使用
     */
    @Test
    public void methodReference() {

        // 构造器引用。语法是Class::new，或者更一般的Class< T >::new，要求构造器方法是没有参数；
        final Car car = Car.create(Car::new);
        final List<Car> cars = Arrays.asList(car);

        //静态方法引用。语法是Class::static_method，要求接受一个Class类型的参数；
        cars.forEach(Car::collide);

        //任意对象的方法引用。它的语法是Class::instance_method。无参，所有元素调用；
        cars.forEach(Car::repair);

        //特定对象的方法引用，它的语法是instance::instance_method。
        // 有参，在某个对象上调用方法，将列表元素作为参数传入；第一个参数当成instanceMethod的目标对象，其他剩余参数当成该方法的参数
        final Car police = Car.create(Car::new);
        cars.forEach(police::follow);  // 等于 x -> x.follow(), 此处police其实没有参与调用
    }

    public static class Car {

        public Car() {
            System.out.println("constructor without parameter.");
        }

        public Car(String name) {
            System.out.println("constructor with parameter.");
        }

        public static Car create(final Supplier<Car> supplier) {
            return supplier.get();
        }

        public static void collide(final Car car) {
            System.out.println("static method reference: " + car.toString());
        }

        public void repair() {
            System.out.println("instance method: " + this.toString());
        }

        public void follow(final Car car) {
            System.out.println("simple method " + car.toString());
        }
    }
}
