package com.yee.study.mysoa.spring.bean;

/**
 * 服务注册定义类
 * 
 * @author Roger.Yi
 */
public class Registry {

    private String protocol;

    private String address;

    @Override
    public String toString() {
        return "Registry{" + "protocol='" + protocol + '\'' + ", address='" + address + '\'' + '}';
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
