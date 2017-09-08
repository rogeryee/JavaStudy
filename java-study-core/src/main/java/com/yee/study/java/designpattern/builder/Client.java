package com.yee.study.java.designpattern.builder;

/**
 * 创建模式客户端类
 *
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        ComputerBuilder macBuilder = new MacBuilder();
        Computer macbook = new Director(macBuilder).createComputer();
        System.out.println(macbook);

        ComputerBuilder dellBuilder = new DellBuilder();
        Computer dell = new Director(dellBuilder).createComputer();
        System.out.println(dell);
    }
}
