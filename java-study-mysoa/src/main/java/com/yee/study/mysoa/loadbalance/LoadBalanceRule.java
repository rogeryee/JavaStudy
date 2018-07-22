package com.yee.study.mysoa.loadbalance;

import com.yee.study.mysoa.registry.RegistryService;

import java.util.List;

/**
 * 负载均和策略定义
 * 
 * @author Roger.Yi
 */
public interface LoadBalanceRule {

    /**
     * 获取服务节点
     * @param serviceList
     * @return
     */
    RegistryService select(List<RegistryService> serviceList);
}
