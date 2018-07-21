package com.yee.study.mysoa.registry;

import com.yee.study.mysoa.spring.bean.Registry;
import org.springframework.context.ApplicationContext;

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
    public static void register(RegistryArg arg) {
        Registry registryBean = arg.getContext().getBean(Registry.class);
        RegistryCenter registryCenter = RegistryCenterFactory.get(registryBean.getType());
        registryCenter.register(arg);
    }
}
