package com.yee.study.java.core.jvm;

/**
 * 常量池示例
 * 
 * @author Roger.Yi
 */
public class ConstantsPoolSample
{
    private void test1()
    {
        String s1 = "Hello";
        String s2 = "Hello";
        String s3 = "Hel" + "lo";
        String s4 = "Hel" + new String("lo");
        String s5 = new String("Hello");
        String s6 = s5.intern();
        String s7 = "H";
        String s8 = "ello";
        String s9 = s7 + s8;

        // == 操作符，比较的是两个字符串的引用地址，并不是比较内容
        System.out.println(s1 == s2);  // true s1、s2在赋值时，均使用的字符串字面量，在编译期间，会直接放入class文件的常量池中，从而实现复用，载入运行时常量池后，s1、s2指向的是同一个内存地址
        System.out.println(s1 == s3);  // true s3虽然是动态拼接出来的字符串，但是所有参与拼接的部分都是已知的字面量，在编译期间，这种拼接会被优化，编译器直接帮你拼好，因此String s3 = "Hel" + "lo";在class文件中被优化成String s3 = "Hello"
        System.out.println(s1 == s4);  // false s4虽然也是拼接出来的，但new String("lo")这部分不是已知字面量，是一个不可预料的部分，编译器不会优化，必须等到运行时才可以确定结果
        System.out.println(s1 == s9);  // false s7、s8作为两个变量，都是不可预料的，所以不做优化，等到运行时，s7、s8拼接成的新字符串
        System.out.println(s4 == s5);  // false 二者都在堆中，但地址不同
        System.out.println(s1 == s6);  // true intern方法会尝试将Hello字符串添加到常量池中，并返回其在常量池中的地址，因为常量池中已经有了Hello字符串，所以intern方法直接返回地址；而s1在编译期就已经指向常量池了，因此s1和s6指向同一地址，相等
    }

    private void test2()
    {
        // 使用引号声明的字符串都是会直接在字符串常量池中生成的，而 new 出来的 String 对象是放在堆空间中的
        // JDK1.6中，调用intern方法后，如果发现常量池中没有，则会在常量池中创建一个新的对象，这个对象和堆中的原对象是不同的
        // JDK1.7中，调用intern方法后，常量池中不需要再存储一份对象了，可以直接存储堆中的引用。

        String s = new String("1"); // 生成了常量池中的“1” 和 堆空间中的字符串对象，s指向了堆中对象
        s.intern(); // s对象去常量池中寻找后发现"1"已经存在于常量池中了
        String s2 = "1";  // 生成一个s2的引用指向常量池中的“1”对象
        System.out.println(s == s2); // s 和 s2 的引用地址明显不同

        String s3 = new String("1") + new String("1"); // 常量池中生成“1” ，并在堆空间中生成s3引用指向的对象（内容为"11"）。注意此时常量池中是没有 “11”对象的。
        s3.intern(); // 将 s3中的“11”字符串放入 String 常量池中，JDK1.7中，常量池中不需要再存储一份对象了，可以直接存储堆中的引用（JDK1.6的做法是直接在常量池中生成一个 "11" 的对象）
        String s4 = "11"; // 去常量池中创建，但是发现已经有这个对象了，此时也就是指向 s3 引用对象的一个引用
        System.out.println(s3 == s4);
    }

    private void test3()
    {
        String s = new String("1"); // 生成了常量池中的“1” 和 堆空间中的字符串对象，s指向了堆中对象
        String s2 = "1"; // 生成一个s2的引用指向常量池中的“1”对象，但是发现已经存在了，那么就直接指向了它。
        s.intern(); // 没什么实际作用了，因为"1"已经存在了。
        System.out.println(s == s2); // s 和 s2 的引用地址明显不同

        String s3 = new String("1") + new String("1"); // 在字符串常量池中生成“1” ，并在堆空间中生成s3引用指向的对象（内容为"11"）。注意此时常量池中是没有 “11”对象的。
        String s4 = "11";  // 直接去生成常量池中的"11"
        s3.intern(); // 没什么实际作用了，因为"11"已经存在了
        System.out.println(s3 == s4); // s3 和 s4 的引用地址明显不同
    }

    public static void main(String[] args)
    {
        ConstantsPoolSample sample = new ConstantsPoolSample();
//        sample.test1();
//        sample.test2();
        sample.test3();
    }
}
