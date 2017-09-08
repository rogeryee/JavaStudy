package com.yee.study.java.designpattern.factory.abstractFactory;

/**
 * Windows系统按钮
 *
 * @author RogerYee
 */
public class WinButton implements Button
{
    @Override
    public void click()
    {
        System.out.println("WinButton clicked.");
    }
}
