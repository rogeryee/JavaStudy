package com.yee.study.java.designpattern.adapter.interfaceadapter;

/**
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        Target target = new DefaultTargetWrapper();
        target.doSomething();
        target.doOtherthing();
    }
}
