package com.yee.study.designpattern.proxy.dynamic;

import java.lang.reflect.Proxy;

/**
 * 本例展示了如何使用JDK来实现动态代理
 * <p/>
 * User: RogerYee
 */
public class Client
{
    public static void main(String[] args)
    {
        Subject subject = new RealSubject();
        SubjectHandler handler = new SubjectHandler(subject);

        Subject proxy = (Subject) Proxy.newProxyInstance(subject.getClass().getClassLoader(), subject.getClass()
                .getInterfaces(), handler);

        proxy.request();
    }
}
