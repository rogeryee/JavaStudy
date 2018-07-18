package com.yee.study.mysoa.spring.bean;

/**
 * 协议定义类
 *
 * @author Roger.Yi
 */
public class Protocol {

    private String name;

    private String port;

    private String host;

    @Override
    public String toString() {
        return "Protocol{" + "name='" + name + '\'' + ", port='" + port + '\'' + ", host='" + host + '\'' + '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
