package com.yee.study.java.designpattern.proxy.stat;

/**
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        Subject subject = new Proxy(new RealSubject());
        subject.request();
    }
}
