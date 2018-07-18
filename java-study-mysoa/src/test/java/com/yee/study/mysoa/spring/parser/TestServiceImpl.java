package com.yee.study.mysoa.spring.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试服务实现类
 *
 * @author Roger.Yi
 */
public class TestServiceImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    public void hello(String name) {
        String ret = "Hi, " + name;
        logger.info(ret);
    }
}
