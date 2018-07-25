package com.yee.study.mysoa.integration.consumer;

import com.yee.study.mysoa.integration.common.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 服务
 *
 * @author Roger.Yi
 */
public class ConsumerApp {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerApp.class);

    public static void main(String[] args) {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "integration/consumer.xml" });
        HelloService helloService = context.getBean(HelloService.class);
        helloService.sayHello("Roger");
        helloService.sayHello("Roger");
        helloService.sayHello("Roger");
        helloService.sayHello("Roger");
    }
}
