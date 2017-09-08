package com.yee.study.java.designpattern.template;

/**
 * @author Roger.Yi
 */
public abstract class AbstractFunction
{
    /**
     * 主方法定义方法的结构
     */
    public void mainFunc()
    {
        func1();

        func2();
    }

    abstract void func1();

    abstract void func2();
}
