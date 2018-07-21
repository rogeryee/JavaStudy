package com.yee.study.mysoa.registry;

import com.yee.study.mysoa.consts.RegistryTypes;

import java.util.HashMap;
import java.util.Map;

/**
 * 注册中心工厂类
 *
 * @author Roger.Yi
 */
public class RegistryCenterFactory {

    private static Map<String, RegistryCenter> registryCenterMap = new HashMap<>();

    static {
        registryCenterMap.put(RegistryTypes.REDIS, new RedisRegistryCenter());
    }

    public static  RegistryCenter get(String type) {
        return registryCenterMap.get(type);
    }
}
