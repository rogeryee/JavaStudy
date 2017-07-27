package com.yee.study.designpattern.flyweight;

import java.util.HashMap;
import java.util.Map;

/**
 * 城市工厂类
 * @author Roger.Yi
 */
public class CityFactory
{
    private static Map<String, City> cities = new HashMap<>();

    public static City getCity(String name)
    {
        if(cities.containsKey(name))
        {
            return cities.get(name);
        }
        else
        {
            City city = new City(name);
            cities.put(name, city);
            return city;
        }
    }
}
