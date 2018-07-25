package com.yee.study.mysoa.invoke;

import com.yee.study.mysoa.loadbalance.LoadBalanceFactory;
import com.yee.study.mysoa.loadbalance.LoadBalanceRule;
import com.yee.study.mysoa.registry.RegistryService;
import com.yee.study.mysoa.rpc.netty.NettyUtil;
import com.yee.study.mysoa.spring.bean.Protocol;
import com.yee.study.mysoa.spring.bean.Reference;

import java.util.List;

/**
 * 基于Netty方式的远程调用类
 *
 * @author Roger.Yi
 */
public class NettyInvoke implements Invoke {

    @Override
    public String invoke(InvocationArg arg) throws Exception {

        Reference reference = arg.getReference();
        List<RegistryService> regServices = reference.getRegServices(); // 远端注册的生产者服务

        String loadBalance = reference.getLoadBalance(); // 负载均衡策略
        LoadBalanceRule loadBalanceRule = LoadBalanceFactory.getRule(loadBalance);
        RegistryService registryService = loadBalanceRule.select(regServices);

        Protocol protocol = registryService.getProtocol();
        return NettyUtil.sendMsg(protocol.getHost(),
                protocol.getPort(),
                "Roger");
    }
}
