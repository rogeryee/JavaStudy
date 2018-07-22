package com.yee.study.mysoa.spring.bean;

import com.yee.study.mysoa.invoke.Invoke;
import com.yee.study.mysoa.invoke.InvokeFactory;
import com.yee.study.mysoa.invoke.InvokeInvocationHandler;
import com.yee.study.mysoa.registry.RegistryDelegate;
import com.yee.study.mysoa.registry.RegistryService;
import com.yee.study.util.StringUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * 引用关联定义类
 *
 * 消费者端配置，引用了生产者服务的配置
 *
 * @author Roger.Yi
 */
public class Reference implements Serializable, FactoryBean, ApplicationContextAware, InitializingBean {

    /**
     * ID
     */
    private String id;

    /**
     * 服务接口
     */
    private String intfClazz;

    /**
     * 负载策略
     */
    private String loadBalance;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 获取注册
     */
    private List<RegistryService> regServices;

    /**
     * SpringContext
     */
    private ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    /**
     * 返回intfClazz的代理对象
     *
     * @return
     * @throws Exception
     */
    @Override
    public Object getObject() throws Exception {
        Invoke invoke = InvokeFactory.getInvoke(protocol);
        Object obj = Proxy.newProxyInstance(this.getClass()
                .getClassLoader(), new Class<?>[]{getObjectType()}, new InvokeInvocationHandler(invoke, this));
        return obj;
    }

    @Override
    public Class<?> getObjectType() {

        if (StringUtil.isNotBlank(intfClazz)) {
            try {
                return Class.forName(intfClazz);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("No interface specified, intf = " + intfClazz);
            }
        }

        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        regServices = RegistryDelegate.getRegistry(intfClazz, context);
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

    public String getIntfClazz() {
        return intfClazz;
    }

    public void setIntfClazz(String intfClazz) {
        this.intfClazz = intfClazz;
    }

    public List<RegistryService> getRegServices() {
        return regServices;
    }

    public void setRegServices(List<RegistryService> regServices) {
        this.regServices = regServices;
    }
}