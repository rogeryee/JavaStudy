package com.yee.study.mysoa.spring.bean;

/**
 * 服务定义类
 * 
 * @author Roger.Yi
 */
public class Service {

    private String intf;

    private String ref;

    private String protocol;

    @Override
    public String toString() {
        return "Service{" + "intf='" + intf + '\'' + ", ref='" + ref + '\'' + ", protocol='" + protocol + '\'' + '}';
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
