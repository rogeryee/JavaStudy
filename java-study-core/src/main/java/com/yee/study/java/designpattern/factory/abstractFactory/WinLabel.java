package com.yee.study.java.designpattern.factory.abstractFactory;

/**
 * Windows系统标签类
 *
 * @author RogerYee
 */
public class WinLabel implements Label
{
    @Override
    public void show()
    {
        System.out.println("WinLabel shown.");
    }
}
