package com.yee.study.designpattern.facade;

/**
 * 电视类
 * @author Roger.Yi
 */
public class Television
{
    /**
     * 开电视
     */
    public void turnOn()
    {
        System.out.println("Television turn on.");
    }

    /**
     * 关电视
     */
    public void turnOff()
    {
        System.out.println("Television turn off.");
    }
}
