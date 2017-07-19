package com.yee.study.designpattern.factory.factoryMethod;

/**
 * 产品A工厂类
 *
 * @author RogerYee
 */
public class SampleAFactory implements Factory
{
    @Override
    public Sample create()
    {
        return new SampleA();
    }
}
