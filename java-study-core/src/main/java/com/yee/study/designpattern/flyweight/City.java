package com.yee.study.designpattern.flyweight;

/**
 * 城市类
 * @author Roger.Yi
 */
public class City
{
    private String name;

    private String population;

    public City(String name)
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

    public String getPopulation()
    {
        return population;
    }

    public void setPopulation(String population)
    {
        this.population = population;
    }
}
