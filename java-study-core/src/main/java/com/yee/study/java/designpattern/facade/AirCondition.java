package com.yee.study.java.designpattern.facade;

/**
 * 空调类
 * @author Roger.Yi
 */
public class AirCondition
{
    /**
     * 开空调
     */
    public void turnOn()
    {
        System.out.println("AirCondition turn on.");
    }

    /**
     * 关空调
     */
    public void turnOff()
    {
        System.out.println("AirCondition turn off.");
    }
}
