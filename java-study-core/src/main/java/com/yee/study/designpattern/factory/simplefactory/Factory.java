package com.yee.study.designpattern.factory.simplefactory;

/**
 * 简单工厂类
 *
 * @author RogerYee
 */
public class Factory
{
    /**
     * 创建产品实例
     *
     * @param which
     * @return
     */
    public static Sample create(int which)
    {
        if(which == 1)
            return new SampleA();
        else
            return new SampleB();
    }
}
