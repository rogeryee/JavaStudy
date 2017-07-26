package com.yee.study.designpattern.bridge;

/**
 * 柴油引擎类
 *
 * @author Roger.Yi
 */
public class DieselEngine implements Engine
{
    @Override
    public void start()
    {
        System.out.println("DieselEngine start.");
    }

    @Override
    public void stop()
    {
        System.out.println("DieselEngine stop.");
    }
}
