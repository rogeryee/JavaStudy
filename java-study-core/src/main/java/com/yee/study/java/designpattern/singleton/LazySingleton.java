package com.yee.study.java.designpattern.singleton;

/**
 * 懒汉模式:使用了延迟加载来保证对象在没有使用之前，是不会进行初始化的。
 * 注意在懒汉模式中需要添加synchronized关键字来保证线程安全。但是使用synchronized会带来性能的代价。
 *
 * @author Roger.Yi
 */
public class LazySingleton
{
    private static LazySingleton singObj = null;

    private LazySingleton()
    {
    }

    public static synchronized LazySingleton getSingleInstance()
    {
        if (null == singObj)
            singObj = new LazySingleton();

        return singObj;
    }
}
