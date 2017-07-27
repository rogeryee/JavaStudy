package com.yee.study.designpattern.template;

/**
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        AbstractFunction funcA = new FunctionA();
        funcA.mainFunc();

        AbstractFunction funcB = new FunctionB();
        funcB.mainFunc();
    }
}
