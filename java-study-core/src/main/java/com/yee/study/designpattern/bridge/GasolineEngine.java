package com.yee.study.designpattern.bridge;

/**
 * 汽油引擎类
 *
 * @author Roger.Yi
 */
public class GasolineEngine implements Engine
{
    @Override
    public void start()
    {
        System.out.println("GasolineEngine start.");
    }

    @Override
    public void stop()
    {
        System.out.println("GasolineEngine stop.");
    }
}
