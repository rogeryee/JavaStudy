package com.yee.study.spring.framework.ioc.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * 示例Bean定义类
 *
 * @author Roger.Yi
 */
@Slf4j
public class City implements InitializingBean {

    private String name = "Shanghai";

    private String country = "China";

    private Person person;

    public City() {}

    public City(Person person) {
        this.person = person;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("City.afterPropertiesSet");
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
