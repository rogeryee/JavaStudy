package com.yee.study.java.designpattern.builder;

/**
 * 电脑组装指导类
 *
 * @author Roger.Yi
 */
public class Director
{
    private ComputerBuilder builder;

    public Director(ComputerBuilder builder)
    {
        this.builder = builder;
    }

    /**
     * 组装电脑
     *
     * @return
     */
    public Computer createComputer()
    {
        builder.buildMainboard();
        builder.buildCpu();
        return builder.create();
    }
}
