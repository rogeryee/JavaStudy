package com.yee.study.java.designpattern.adapter.objectadapter;

/**
 * 类适配器客户端类
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        Target target = new ObjectAdapter(new Adaptee());
        target.doOtherthing();
        target.doSomething();
    }
}
