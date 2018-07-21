package com.yee.study.mysoa.registry;

import org.springframework.context.ApplicationContext;

/**
 * 注册中心接口定义类
 *
 * @author Roger.Yi
 */
public interface RegistryCenter {

    /**
     * 服务注册
     *
     * @param params
     * @param context
     * @return
     */
    boolean register(RegistryArg arg);
}
