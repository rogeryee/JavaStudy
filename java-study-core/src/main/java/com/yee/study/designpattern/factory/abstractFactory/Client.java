package com.yee.study.designpattern.factory.abstractFactory;

/**
 * 抽象工厂客户端类
 *
 * @author RogerYee
 */
public class Client
{
    public static void main(String [] args)
    {
        Factory factory = new UnixFactory();

        Button button = factory.createButton();
        button.click();

        Label label = factory.createLabel();
        label.show();
    }
}
