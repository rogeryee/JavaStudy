package com.yee.study.designpattern.factory.abstractFactory;

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
