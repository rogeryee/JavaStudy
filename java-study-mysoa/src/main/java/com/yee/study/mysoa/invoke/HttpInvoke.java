package com.yee.study.mysoa.invoke;

import com.yee.study.mysoa.loadbalance.LoadBalanceFactory;
import com.yee.study.mysoa.loadbalance.LoadBalanceRule;
import com.yee.study.mysoa.registry.RegistryService;
import com.yee.study.mysoa.spring.bean.Reference;

import java.util.List;

/**
 * 基于Http方式的远程调用类
 *
 * @author Roger.Yi
 */
public class HttpInvoke implements Invoke {

    @Override
    public String invoke(InvocationArg arg) {

        Reference reference = arg.getReference();
        List<RegistryService> regServices = reference.getRegServices(); // 远端注册的生产者服务

        String loadBalance = reference.getLoadBalance(); // 负载均衡策略
        LoadBalanceRule loadBalanceRule = LoadBalanceFactory.getRule(loadBalance);
        RegistryService registryService = loadBalanceRule.select(regServices);

        return null;
    }
}
