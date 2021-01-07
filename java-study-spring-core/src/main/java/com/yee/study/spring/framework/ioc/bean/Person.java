package com.yee.study.spring.framework.ioc.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * 示例Bean定义类
 *
 * @author Roger.Yi
 */
@Slf4j
public class Person implements InitializingBean, DisposableBean {

    private String name;

    private String address;

    public Person() {
        this.name = "Roger";
        this.address = "Shanghai";
        log.info("Person.newInstance()");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("Person.afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
        log.info("Person.destroy");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
