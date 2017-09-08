package com.yee.study.java.designpattern.factory.abstractFactory;

/**
 * Unix系统标签类
 *
 * @author RogerYee
 */
public class UnixLabel implements Label
{
    @Override
    public void show()
    {
        System.out.println("UnixLabel shown.");
    }
}
