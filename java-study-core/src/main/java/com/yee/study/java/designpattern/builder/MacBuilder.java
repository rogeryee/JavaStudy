package com.yee.study.java.designpattern.builder;

/**
 * Mac电脑组装类
 *
 * @author Roger.Yi
 */
public class MacBuilder extends ComputerBuilder
{
    private Computer computer = new Computer();

    @Override
    void buildMainboard()
    {
        computer.setMainboard(new Mainboard("Macbook"));
    }

    @Override
    void buildCpu()
    {
        computer.setCpu(new Cpu("Intel Core i5"));
    }

    @Override
    Computer create()
    {
        return computer;
    }
}
