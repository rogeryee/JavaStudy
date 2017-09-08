package com.yee.study.java.designpattern.singleton;

/**
 * 懒汉模式(DCL):使用双重检查锁（Double-Checked Lock）的方式来改进。
 * 但实际上DCL的程序在并发时依然会有问题，比如线程A、B同时调用该方法，而线程A正在初始化对象，但还没完成，这时线程B会获得一个没有完成初始化的对象。
 *
 * @author Roger.Yi
 */
public class DoubleCheckedSingleton
{
    private static DoubleCheckedSingleton singObj = null;

    private DoubleCheckedSingleton()
    {
    }

    public static DoubleCheckedSingleton getSingleInstance()
    {
        if (null == singObj)
        {
            synchronized (DoubleCheckedSingleton.class)
            {
                if (null == singObj)
                    singObj = new DoubleCheckedSingleton();
            }
        }
        return singObj;
    }
}
