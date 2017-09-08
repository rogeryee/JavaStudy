package com.yee.study.java.designpattern.singleton;

/**
 * 饥饿型：存在潜在的性能问题
 *
 * @author RogerYee
 */
public class EagerSingleton
{
    private static EagerSingleton singObj = new EagerSingleton();

    private EagerSingleton()
    {
    }

    public static EagerSingleton getSingleInstance()
    {
        return singObj;
    }
}
