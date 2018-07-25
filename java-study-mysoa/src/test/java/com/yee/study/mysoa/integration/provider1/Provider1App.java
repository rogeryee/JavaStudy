package com.yee.study.mysoa.integration.provider1;

import com.yee.study.mysoa.mock.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 服务
 *
 * @author Roger.Yi
 */
public class Provider1App {

    private static final Logger logger = LoggerFactory.getLogger(Provider1App.class);

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "integration/provider-1.xml" });
        context.registerShutdownHook();
    }
}
