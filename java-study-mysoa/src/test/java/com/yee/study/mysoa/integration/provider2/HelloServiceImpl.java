package com.yee.study.mysoa.integration.provider2;

import com.yee.study.mysoa.integration.common.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Roger.Yi
 */
public class HelloServiceImpl implements HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloServiceImpl.class);
    
    @Override
    public String sayHello(String name) {
        logger.info("Provider2->HelloService.sayHello()");
        return null;
    }
}
