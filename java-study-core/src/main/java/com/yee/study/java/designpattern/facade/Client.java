package com.yee.study.java.designpattern.facade;

/**
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        Power power = new Power();
        power.on();
        power.off();
    }
}
