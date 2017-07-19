package com.yee.study.designpattern.builder;

/**
 * 戴尔电脑组装类
 *
 * @author Roger.Yi
 */
public class DellBuilder extends ComputerBuilder
{
    private Computer computer = new Computer();

    @Override
    void buildMainboard()
    {
        computer.setMainboard(new Mainboard("Dell"));
    }

    @Override
    void buildCpu()
    {
        computer.setCpu(new Cpu("Intel Core i7"));
    }

    @Override
    Computer create()
    {
        return computer;
    }
}
