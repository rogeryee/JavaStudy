package com.yee.study.mysoa.spring.parser;

import com.yee.study.mysoa.spring.bean.Protocol;
import com.yee.study.mysoa.spring.bean.Reference;
import com.yee.study.mysoa.spring.bean.Registry;
import com.yee.study.mysoa.spring.bean.Service;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Roger.Yi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/parser/mytest.xml"})
public class MySOAParserTest {

    private static final Logger logger = LoggerFactory.getLogger(MySOAParserTest.class);

    @Autowired
    private TestService testService;

    @Autowired
    private Registry registry;

    @Autowired
    private Protocol protocol;

    @Autowired
    private Service service;

    @Autowired
    private Reference reference;

    @Test
    public void testParser() {
        testService.hello("Roger");
        logger.info(service.toString());
        logger.info(reference.toString());
        logger.info(protocol.toString());
        logger.info(registry.toString());
    }
}
