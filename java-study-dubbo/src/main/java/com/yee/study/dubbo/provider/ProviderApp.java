package com.yee.study.dubbo.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 服务提供者应用
 *
 * @author Roger.Yi
 */
public class ProviderApp {

    private static final Logger logger = LoggerFactory.getLogger(ProviderApp.class);

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "provider/provider.xml" });
        context.registerShutdownHook();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
