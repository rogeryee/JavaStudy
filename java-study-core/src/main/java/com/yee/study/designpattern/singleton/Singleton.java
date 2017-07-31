package com.yee.study.designpattern.singleton;

/**
 * 内部类实现的单例模式
 *
 * @author Roger.Yi
 */
public class Singleton
{
    // 采用了一种新的模式——Initialization on Demand Holder.
    // 这种方法使用内部类来做到延迟加载对象，在初始化这个内部类的时候，JLS(Java Language Sepcification)会保证这个类的线程安全。
    private static class SingletonHolder
    {
        public final static Singleton instance = new Singleton();
    }

    private Singleton(){}

    public static Singleton getInstance()
    {
        return SingletonHolder.instance;
    }
}
