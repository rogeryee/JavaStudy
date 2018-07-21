package com.yee.study.mysoa.spring.parser;

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
 * mysoa 标签解析测试类
 *
 * @author Roger.Yi
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/parser/parser-test.xml"})
public class ParserTest {

    private static final Logger logger = LoggerFactory.getLogger(ParserTest.class);

    @Autowired
    private ApplicationContext context;

    @Test
    public void testParser() {
        TestService testService = context.getBean(TestService.class);
        testService.hello("Roger");
    }
}
