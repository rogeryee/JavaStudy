package com.yee.study.mysoa.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试服务实现类
 *
 * @author Roger.Yi
 */
public class TestServiceProviderImpl implements TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestServiceProviderImpl.class);

    @Override
    public void hello(String name) {
        String ret = "Hi, " + name;
        logger.info(ret);
    }

    @Override
    public String toString() {
        return "TestServiceImpl{}";
    }
}
