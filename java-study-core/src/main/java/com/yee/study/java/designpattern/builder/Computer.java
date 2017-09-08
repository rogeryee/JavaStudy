package com.yee.study.java.designpattern.builder;

/**
 * 电脑类
 *
 * @author Roger.Yi
 */
public class Computer
{
    private Mainboard mainboard;

    private Cpu cpu;

    @Override
    public String toString()
    {
        return mainboard.getName() + ": " + cpu.getName();
    }

    public Cpu getCpu()
    {
        return cpu;
    }

    public void setCpu(Cpu cpu)
    {
        this.cpu = cpu;
    }

    public Mainboard getMainboard()
    {
        return mainboard;
    }

    public void setMainboard(Mainboard mainboard)
    {
        this.mainboard = mainboard;
    }
}
