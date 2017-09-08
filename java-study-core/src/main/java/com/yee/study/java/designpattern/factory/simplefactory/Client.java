package com.yee.study.java.designpattern.factory.simplefactory;

/**
 * 简单工厂客户端类
 *
 * @author Roger.Yee
 */
public class Client
{
    public static void main(String [] args)
    {
        Sample sample = Factory.create(1);
        sample.action();
    }
}
