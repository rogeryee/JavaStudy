package com.yee.study.designpattern.adapter.classadapter;

/**
 * 类适配器客户端类
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        Target target = new ClassAdapter();
        target.doOtherthing();
        target.doSomething();
    }
}
