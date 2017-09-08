package com.yee.study.java.designpattern.adapter.interfaceadapter;

/**
 * @author Roger.Yi
 */
public abstract class AbstractTargetWrapper implements Target
{
    @Override
    public void doSomething()
    {
        System.out.println("AbstractWrapper doSomething.");
    }
}
