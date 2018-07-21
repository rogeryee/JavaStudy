package com.yee.study.mysoa.spring.bean;

import com.yee.study.mysoa.registry.RegistryDelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;

/**
 * 服务定义类
 *
 * @author Roger.Yi
 */
public class Service implements Serializable, ApplicationContextAware {

    /**
     * 编号
     */
    public String id;

    /**
     * 接口
     */
    private String intfClazz;

    /**
     * 引用对象
     */
    private String ref;

    /**
     * 协议
     */
    private String protocol;

    private ApplicationContext context;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntfClazz() {
        return intfClazz;
    }

    public void setIntfClazz(String intfClazz) {
        this.intfClazz = intfClazz;
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
