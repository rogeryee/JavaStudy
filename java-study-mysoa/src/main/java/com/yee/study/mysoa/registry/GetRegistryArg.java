package com.yee.study.mysoa.registry;

import com.yee.study.mysoa.spring.bean.Service;
import org.springframework.context.ApplicationContext;

/**
 * 获取注册服务参数定义类
 * 
 * @author Roger.Yi
 */
public class GetRegistryArg {

    private String serviceName;

    private ApplicationContext context;

    public static GetRegistryArg newArgs(String serviceName, ApplicationContext context) {
        GetRegistryArg arg = new GetRegistryArg();
        arg.setContext(context);
        arg.setServiceName(serviceName);
        return arg;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
