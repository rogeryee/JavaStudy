package com.yee.study.java.designpattern.decorator;

/**
 * @author RogerYee
 */
public class Client
{
    public static void main(String [] args)
    {
        Component component = new ConcreteComponent();
        Decorator decorator = new Decorator(component);
        decorator.operation();
    }
}
