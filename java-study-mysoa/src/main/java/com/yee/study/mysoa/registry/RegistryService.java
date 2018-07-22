package com.yee.study.mysoa.registry;

import com.yee.study.mysoa.spring.bean.Protocol;
import com.yee.study.mysoa.spring.bean.Service;

import java.io.Serializable;

/**
 * 可序列化的注册服务描述
 * 
 * @author Roger.Yi
 */
public class RegistryService implements Serializable {

    private Protocol protocol;

    private Service service;

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
