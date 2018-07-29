package com.yee.study.dubbo.provider.service;

import com.yee.study.dubbo.common.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello 服务实现类
 *
 * @author Roger.Yi
 */
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);

    @Override
    public String sayHello(String name) {
        logger.info("request.name = " + name);
        return "Hi, " + name;
    }
}
