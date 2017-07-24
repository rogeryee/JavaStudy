package com.yee.study.designpattern.proxy.stat;

/**
 * 委托类
 *
 * @author Roger.Yi
 */
public class RealSubject implements Subject
{
    @Override
    public void request()
    {
        System.out.println("RealSubject request.");
    }
}
