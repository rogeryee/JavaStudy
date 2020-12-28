package com.yee.study.java.proxy.objenesis;

/**
 * Objenesis测试Bean定义类
 *
 * @author Roger.Yi
 */
public class ObjectSampleBean {

    private String name = "Roger"; // default

    private int id = 10; // default

    public ObjectSampleBean(String name, int id) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
