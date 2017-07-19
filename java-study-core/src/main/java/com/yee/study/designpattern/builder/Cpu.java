package com.yee.study.designpattern.builder;

/**
 * CPU类
 *
 * @author Roger.Yi
 */
public class Cpu
{
    private String name;

    public Cpu(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
