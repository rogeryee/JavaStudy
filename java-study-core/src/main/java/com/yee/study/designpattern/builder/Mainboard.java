package com.yee.study.designpattern.builder;

/**
 * 主板类
 *
 * @author Roger.Yi
 */
public class Mainboard
{
    private String name;

    public Mainboard(String name)
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
