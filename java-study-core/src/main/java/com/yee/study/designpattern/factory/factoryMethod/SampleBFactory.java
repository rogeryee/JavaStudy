package com.yee.study.designpattern.factory.factoryMethod;

/**
 * 产品B工厂类
 *
 * @author RogerYee
 */
public class SampleBFactory implements Factory
{
    @Override
    public Sample create()
    {
        return new SampleA();
    }
}
