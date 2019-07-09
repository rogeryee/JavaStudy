package com.yee.study.spring.boot.samples.mongo.model;

import org.springframework.data.annotation.Id;

/**
 * 用户定义类
 * 
 * @author Roger.Yi
 */
public class UserInfo {

    @Id
    private String id;

    private String name;

    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
