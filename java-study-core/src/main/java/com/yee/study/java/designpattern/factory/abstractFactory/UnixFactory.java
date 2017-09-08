package com.yee.study.java.designpattern.factory.abstractFactory;

/**
 * Unix系统工厂类
 *
 * @author RogerYee
 */
public class UnixFactory implements Factory
{
    @Override
    public Button createButton()
    {
        return new UnixButton();
    }

    @Override
    public Label createLabel()
    {
        return new UnixLabel();
    }
}
