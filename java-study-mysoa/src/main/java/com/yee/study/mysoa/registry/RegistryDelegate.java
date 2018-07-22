package com.yee.study.mysoa.registry;

import com.yee.study.mysoa.spring.bean.Protocol;
import com.yee.study.mysoa.spring.bean.Registry;
import com.yee.study.mysoa.spring.bean.Service;
import org.springframework.context.ApplicationContext;

import java.util.List;
import java.util.Map;

/**
 * @author Roger.Yi
 */
public class RegistryDelegate {

    /**
     * 服务注册
     *
     * @param ref
     * @param context
     */
    public static void register(ApplicationContext context) {
        Protocol protocol = context.getBean(Protocol.class);
        Map<String, Service> serviceBeans = context.getBeansOfType(Service.class);

        for (Map.Entry<String, Service> entry : serviceBeans.entrySet()) {
            // 注册服务到注册中心
            RegistryArg arg = RegistryArg.newArgs(entry.getValue(), context);
            Registry registryBean = arg.getContext().getBean(Registry.class);
            RegistryCenter registryCenter = RegistryCenterFactory.get(registryBean.getType());
            registryCenter.register(arg);
        }
    }

    /**
     * 获取注册的服务
     *
     * @param name
     * @return
     */
    public static List<RegistryService> getRegistry(String name, ApplicationContext context) {
        Protocol protocol = context.getBean(Protocol.class);
        GetRegistryArg arg = GetRegistryArg.newArgs(name, context);
        Registry registryBean = arg.getContext().getBean(Registry.class);
        RegistryCenter registryCenter = RegistryCenterFactory.get(registryBean.getType());
        return registryCenter.getRegistry(arg);
    }
}
