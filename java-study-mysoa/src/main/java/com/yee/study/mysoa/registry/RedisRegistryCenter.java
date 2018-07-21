package com.yee.study.mysoa.registry;

import com.yee.study.mysoa.redis.RedisApi;
import com.yee.study.mysoa.spring.bean.Protocol;
import com.yee.study.mysoa.spring.bean.Registry;
import com.yee.study.mysoa.spring.bean.Service;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * 基于Redis的注册中心
 *
 * @author Roger.Yi
 */
public class RedisRegistryCenter implements RegistryCenter {
    
    @Override
    public boolean register(RegistryArg arg) {
        ApplicationContext context = arg.getContext();
        Protocol protocol = context.getBean(Protocol.class);

        Registry registryBean = arg.getContext().getBean(Registry.class);
        RedisApi.createJedisPool(registryBean.getAddress());

        return false;
    }
}
