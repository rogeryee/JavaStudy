package com.yee.study.designpattern.flyweight;

/**
 * @author Roger.Yi
 */
public class Client
{
    public static void main(String[] args)
    {
        City shanghai = CityFactory.getCity("Shanghai");
        System.out.println(shanghai.getName());
    }
}
