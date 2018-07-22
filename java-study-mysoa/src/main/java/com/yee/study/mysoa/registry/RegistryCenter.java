package com.yee.study.mysoa.registry;

import java.util.List;

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

    /**
     * 获取注册的服务
     *
     * @param name
     * @return
     */
    List<RegistryService> getRegistry(GetRegistryArg arg);
}
