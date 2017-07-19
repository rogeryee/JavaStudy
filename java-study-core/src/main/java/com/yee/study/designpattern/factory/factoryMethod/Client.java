package com.yee.study.designpattern.factory.factoryMethod;

/**
 * 工厂方法客户端类
 *
 * @author RogerYee
 */
public class Client
{
    public static void main(String [] args)
    {
        Factory factoryA = new SampleAFactory();
        Sample sampleA = factoryA.create();
        sampleA.action();
    }
}
