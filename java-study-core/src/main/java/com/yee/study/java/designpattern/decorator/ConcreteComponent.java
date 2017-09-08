package com.yee.study.java.designpattern.decorator;

/**
 * 组件实现类
 *
 * @author RogerYee
 */
public class ConcreteComponent implements Component
{
    @Override
    public void operation()
    {
        System.out.println("ConcreteComponent:operation()");
    }
}
