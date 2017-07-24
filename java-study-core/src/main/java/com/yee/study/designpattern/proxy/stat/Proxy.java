package com.yee.study.designpattern.proxy.stat;

/**
 * 代理类
 *
 * @author Roger.Yi
 */
public class Proxy implements Subject
{
    private Subject subject;

    public Proxy(Subject subject)
    {
        this.subject = subject;
    }

    @Override
    public void request()
    {
        System.out.println("Proxy before request.");
        this.subject.request();
        System.out.println("Proxy before request.");
    }
}
