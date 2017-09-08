package com.yee.study.java.designpattern.facade;

/**
 * 电灯类
 * @author Roger.Yi
 */
public class Light
{
    /**
     * 开灯
     */
    public void turnOn()
    {
        System.out.println("Light turn on.");
    }

    /**
     * 关灯
     */
    public void turnOff()
    {
        System.out.println("Light turn off.");
    }
}
