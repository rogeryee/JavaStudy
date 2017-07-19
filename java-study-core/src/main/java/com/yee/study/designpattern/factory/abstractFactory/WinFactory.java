package com.yee.study.designpattern.factory.abstractFactory;

/**
 * Windows系统工厂类
 *
 * @author RogerYee
 */
public class WinFactory implements Factory
{
    @Override
    public Button createButton()
    {
        return new WinButton();
    }

    @Override
    public Label createLabel()
    {
        return new WinLabel();
    }
}
