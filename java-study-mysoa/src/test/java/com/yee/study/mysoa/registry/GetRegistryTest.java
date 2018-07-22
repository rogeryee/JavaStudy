package com.yee.study.mysoa.registry;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 获取服务注册测试类（消费者端）
 *
 * @author Roger.Yi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:registry/get-registry-test.xml"})
public class GetRegistryTest {

    private static final Logger logger = LoggerFactory.getLogger(GetRegistryTest.class);

    @Autowired
    private ApplicationContext context;

    @Test
    public void testGetRegistry() {
        logger.info("test get registry");
    }
}
