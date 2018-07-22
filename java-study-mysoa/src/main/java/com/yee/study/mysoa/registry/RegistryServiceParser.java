package com.yee.study.mysoa.registry;

import com.yee.study.util.json.JSON;

/**
 * 注册服务描述解析类
 *
 * @author Roger.Yi
 */
public class RegistryServiceParser {

    public static String marshal(RegistryService registryService) {
        return JSON.getDefault().toExplictJSONString(registryService);
    }

    public static RegistryService unmarshal(String registServiceDesc) {
        return JSON.getDefault().parseToExplictObject(registServiceDesc, RegistryService.class);
    }
}
