package com.yee.study.designpattern.factory.factoryMethod;

/**
 * 简单工厂接口类
 *
 * @author RogerYee
 */
public interface Factory
{
    /**
     * 创建产品实例
     * @return
     */
    Sample create();
}
