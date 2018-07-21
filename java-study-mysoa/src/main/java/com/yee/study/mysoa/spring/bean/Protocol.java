package com.yee.study.mysoa.spring.bean;

import java.io.Serializable;

/**
 * 协议定义类
 *
 * @author Roger.Yi
 */
public class Protocol implements Serializable {

    /**
     * ID
     */
    public String id;

    /**
     * 协议类型
     * {@link com.yee.study.mysoa.consts.Protocols}
     */
    private String name;

    /**
     * 端口
     */
    private String port;

    /**
     * Host地址
     */
    private String host;

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
