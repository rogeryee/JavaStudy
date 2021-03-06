package com.yee.study.java.designpattern.factory.abstractFactory;

/**
 * Unix系统按钮
 *
 * @author RogerYee
 */
public class UnixButton implements Button
{
    @Override
    public void click()
    {
        System.out.println("UnixButton clicked.");
    }
}
