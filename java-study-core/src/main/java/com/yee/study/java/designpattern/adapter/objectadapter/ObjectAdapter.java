package com.yee.study.java.designpattern.adapter.objectadapter;

/**
 * 类适配器
 *
 * @author Roger.Yi
 */
public class ObjectAdapter implements Target
{
    private Adaptee adaptee;

    public ObjectAdapter(Adaptee adaptee)
    {
        this.adaptee = adaptee;
    }

    @Override
    public void doSomething()
    {
        System.out.println("ObjectAdapter doSomething.");
        this.adaptee.doSomething();
    }

    @Override
    public void doOtherthing()
    {
        System.out.println("ObjectAdapter doOtherthing.");
    }
}
