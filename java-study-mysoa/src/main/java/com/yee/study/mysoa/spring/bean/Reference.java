package com.yee.study.mysoa.spring.bean;

/**
 * 引用关联定义类
 *
 * @author Roger.Yi
 */
public class Reference {

    private String id;

    private String intf;

    private String loadBalance;

    private String protocol;

    @Override
    public String toString() {
        return "Reference{" + "id='" + id + '\'' + ", intf='" + intf + '\'' + ", loadBalance='" + loadBalance + '\'' + ", protocol='" + protocol + '\'' + '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoadBalance() {
        return loadBalance;
    }

    public void setLoadBalance(String loadBalance) {
        this.loadBalance = loadBalance;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }
}