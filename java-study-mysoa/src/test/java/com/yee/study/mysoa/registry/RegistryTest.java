package com.yee.study.mysoa.registry;

import com.yee.study.mysoa.mock.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 服务注册测试类
 *
 * @author Roger.Yi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:registry/registry-test.xml"})
public class RegistryTest {

    private static final Logger logger = LoggerFactory.getLogger(RegistryTest.class);

    @Autowired
    private ApplicationContext context;

    @Test
    public void testRegistry() {
        TestService testService = context.getBean(TestService.class);
        testService.hello("Roger");
    }
}
