package com.yee.study.spring.framework.ioc.bean;

import lombok.extern.slf4j.Slf4j;

/**
 * FactoryBean
 *
 * @author Roger.Yi
 */
@Slf4j
public class CityFactory {

    /**
     * factory method
     *
     * @return
     */
    public City creatCity() {
        City city = new City();
        city.setName("Default");
        city.setCountry("Default Country");
        return city;
    }
}
