package com.yee.study.mysoa.registry;

import com.yee.study.mysoa.spring.bean.Protocol;
import com.yee.study.mysoa.spring.bean.Service;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * 服务注册初始化类
 *
 * @author Roger.Yi
 */
public class ServiceRegistryInitializer implements InitializingBean, ApplicationContextAware {

    private ApplicationContext context;

    @Override
    public void afterPropertiesSet() throws Exception {
        Protocol protocol = context.getBean(Protocol.class);
        Map<String, Service> serviceBeans = context.getBeansOfType(Service.class);

        for (Map.Entry<String, Service> entry : serviceBeans.entrySet()) {
            // 注册服务到注册中心
            RegistryArg arg = RegistryArg.newArgs(entry.getValue(), context);
            RegistryDelegate.register(arg);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
