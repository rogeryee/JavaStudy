package com.yee.study.java.designpattern.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Roger.Yi
 */
public class SubjectHandler implements InvocationHandler
{
    private Object obj;

    public SubjectHandler(Object obj)
    {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        this.doBefore();

        Object result = method.invoke(this.obj, args);

        this.doAfter();

        return result;
    }

    private void doBefore()
    {
        System.out.println("doBefore...");
    }

    private void doAfter()
    {
        System.out.println("doAfter...");
    }
}
