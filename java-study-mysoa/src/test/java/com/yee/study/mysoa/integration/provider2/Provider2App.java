package com.yee.study.mysoa.integration.provider2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 服务
 *
 * @author Roger.Yi
 */
public class Provider2App {

    private static final Logger logger = LoggerFactory.getLogger(Provider2App.class);

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "integration/provider-2.xml" });
        context.registerShutdownHook();
    }
}
