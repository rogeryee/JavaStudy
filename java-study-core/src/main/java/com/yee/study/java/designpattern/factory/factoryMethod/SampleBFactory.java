package com.yee.study.java.designpattern.factory.factoryMethod;

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
