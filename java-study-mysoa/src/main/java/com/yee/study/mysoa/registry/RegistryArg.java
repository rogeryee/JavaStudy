package com.yee.study.mysoa.registry;

import com.yee.study.mysoa.spring.bean.Service;
import org.springframework.context.ApplicationContext;

/**
 * 注册服务参数定义类
 * 
 * @author Roger.Yi
 */
public class RegistryArg {

    private Service service;

    private ApplicationContext context;

    public static RegistryArg newArgs(Service service, ApplicationContext context) {
        RegistryArg arg = new RegistryArg();
        arg.setContext(context);
        arg.setService(service);
        return arg;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
