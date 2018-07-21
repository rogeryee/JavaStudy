package com.yee.study.mysoa.spring.bean;

import java.io.Serializable;

/**
 * 服务注册定义类
 *
 * @author Roger.Yi
 */
public class Registry implements Serializable {

    /**
     * ID
     */
    public String id;

    /**
     * 注册中心的类型
     */
    private String type;

    /**
     * 注册中心的Host:port
     */
    private String address;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
