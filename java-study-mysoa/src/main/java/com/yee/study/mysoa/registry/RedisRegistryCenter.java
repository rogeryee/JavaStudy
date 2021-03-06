package com.yee.study.mysoa.registry;

import com.yee.study.mysoa.redis.RedisApi;
import com.yee.study.mysoa.spring.bean.Protocol;
import com.yee.study.mysoa.spring.bean.Registry;
import com.yee.study.mysoa.spring.bean.Service;
import com.yee.study.util.CollectionUtil;
import com.yee.study.util.json.JSON;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;
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
        Service service = arg.getService();
        Protocol protocol = context.getBean(Protocol.class);

        Registry registryBean = arg.getContext().getBean(Registry.class);
        RedisApi.createJedisPool(registryBean.getAddress());

        String key = service.getIntfClazz();
        String hashKey = getHashKey(protocol);
        RegistryService registryService = new RegistryService();
        registryService.setProtocol(protocol);
        registryService.setService(service);
        RedisApi.hset(key, hashKey, RegistryServiceParser.marshal(registryService));
        return true;
    }

    @Override
    public List<RegistryService> getRegistry(GetRegistryArg arg) {
        ApplicationContext context = arg.getContext();

        Registry registryBean = arg.getContext().getBean(Registry.class);
        RedisApi.createJedisPool(registryBean.getAddress());

        String key = arg.getServiceName();
        List<String> serviceDesc = RedisApi.hvals(key);

        if(CollectionUtil.isNotEmpty(serviceDesc)) {
            List<RegistryService> registryServices = new ArrayList<>(CollectionUtil.size(serviceDesc));
            for(String desc : serviceDesc) {
                registryServices.add(RegistryServiceParser.unmarshal(desc));
            }
            return registryServices;
        }

        return null;
    }

    private String getHashKey(Protocol protocol) {
        return protocol.getHost() + ":" + protocol.getPort();
    }
}
