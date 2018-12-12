package com.yee.study.java.core.jdk8;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * JDK8 新特性 - Optional
 * <p>
 * Optional是一个可以包含null值的容器对象，可以用来代替xx != null的判断。
 * <p>
 * Optional常用方法
 * of : 为value创建一个Optional对象，如果value为空则 会报出NullPointerException异常。
 * public static <T> Optional<T> of(T value) {
 *      return new Optional<>(value);
 * }
 * <p>
 * ofNullable : 为value创建一个Optional对象，但可以允许value为null值。
 * public static <T> Optional<T> ofNullable(T value) {
 *      return value == null ? empty() : of(value);
 * }
 * <p>
 * isPresent : 判断当前value是否为null,如果不为null则返回true，否则false。
 * public boolean isPresent() {
 *      return value != null;
 * }
 * <p>
 * ifPresent : 如果不为null值就执行函数式接口的内容。
 * public void ifPresent(Consumer<? super T> consumer) {
 *      if (value != null)
 *          consumer.accept(value);
 * }
 * <p>
 * get : 返回当前的值，如果为空则报异常。
 * public T get() {
 *      if (value == null) {
 *          throw new NoSuchElementException("No value present");
 *      }
 *      return value;
 * }
 * <p>
 * orElse : 返回当前值，如果为null则返回other。
 * public T orElse(T other) {
 *      return value != null ? value : other;
 * }
 * <p>
 * orElseGet : orElseGet和orElse类似，只是orElseGet支持函数式接口来生成other值。
 * public T orElseGet(Supplier<? extends T> other) {
 *      return value != null ? value : other.get();
 * }
 * <p>
 * orElseThrow : 如果有值则返回，没有则用函数式接口抛出生成的异常。
 * public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
 *      if (value != null) {
 *          return value;
 *      } else {
 *          throw exceptionSupplier.get();
 *      }
 * }
 *
 * @author Roger.Yi
 */
public class OptionalSample {

    private static final Logger logger = LoggerFactory.getLogger(OptionalSample.class);

    /**
     * 示例
     */
    @Test
    public void test() {
        String str = "Test String";
        Optional<String> opt = Optional.of(str);

        // 1. 存在即返回, 无则提供默认值
        // 无Optional：str == null ? "Default" : str;
        // 错误使用 ： opt.isPresent() ? opt.get() : "Default";
        opt.orElse("Default");

        // 2. 存在才对它做点什么
        // 无Optional：if(str != null) { System.out.println(str) }
        opt.ifPresent(logger::info);

        // 3. map函数
        // 无Optional时，需要对每一个null值都要进行非空判断。
        // User user = .....
        // if(user != null) {
        //  String name = user.getName();
        //  if(name != null) {
        //    return name.toUpperCase();
        //  } else {
        //    return null;
        //  }
        // } else {
        //  return null;
        // }
        User user = new User(null);
        Optional<User> optUser = Optional.ofNullable(user);
        logger.info("optUser.name = {}", optUser.map(u -> u.getName()).map(name -> name.toUpperCase()).orElse(null));

        user = new User("Roger");
        optUser = Optional.ofNullable(user);
        logger.info("optUser.name = {}", optUser.map(u -> u.getName()).map(name -> name.toUpperCase()).orElse(null));
    }

    class User {
        private String name;

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
