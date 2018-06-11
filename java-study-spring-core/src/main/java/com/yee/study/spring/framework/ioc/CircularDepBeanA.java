package com.yee.study.spring.framework.ioc;

/**
 * 循环依赖测试Bean A
 * @author Roger.Yi
 */
public class CircularDepBeanA
{
    private CircularDepBeanB b;

    public CircularDepBeanA(CircularDepBeanB b)
    {
        this.b = b;
    }
}

/**
 * 循环依赖测试Bean B
 */
class CircularDepBeanB
{
    private CircularDepBeanA a;

    public CircularDepBeanB(CircularDepBeanA a)
    {
        this.a = a;
    }
}
