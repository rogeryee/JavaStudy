package com.yee.study.dubbo.consumer;

import com.yee.study.dubbo.common.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 服务提供者应用
 *
 * @author Roger.Yi
 */
public class ConsumerApp {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerApp.class);

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "consumer/consumer.xml" });
        context.registerShutdownHook();

        HelloService helloService = context.getBean(HelloService.class);
        String ret = helloService.sayHello("Roger");
        logger.info("Return string = " + ret);
    }
}
