package com.yee.study.java.proxy.cglib;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 示例Bean定义类
 *
 * @author Roger.Yi
 */
@Slf4j
@Data
public class SampleBean {

    private String name;

    public SampleBean() {

    }

    public SampleBean(String name) {
        this.name = name;
    }

    public void test() {
        log.info("Hello World");
    }

    public String test2() {
        log.info("test2 : hello world.");
        return "Hello World";
    }
}
