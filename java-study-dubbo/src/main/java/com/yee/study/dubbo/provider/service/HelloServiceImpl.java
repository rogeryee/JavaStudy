package com.yee.study.dubbo.provider.service;

import com.yee.study.dubbo.common.HelloService;

/**
 * Hello 服务实现类
 *
 * @author Roger.Yi
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hi, " + name;
    }
}
