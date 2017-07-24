package com.yee.study.designpattern.decorator;

/**
 * 装饰器
 *
 * @author: RogerYee
 */
public class Decorator implements Component
{
    private Component component;

    public Decorator(Component component)
    {
        this.component = component;
    }

    @Override
    public void operation()
    {
        System.out.println("Add feature before operation.");
        this.component.operation();
        System.out.println("Add feature after operation.");
    }
}
