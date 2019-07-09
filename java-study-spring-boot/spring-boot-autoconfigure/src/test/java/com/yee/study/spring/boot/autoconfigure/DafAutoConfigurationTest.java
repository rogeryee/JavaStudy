package com.yee.study.spring.boot.autoconfigure;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApp.class})
//@ContextConfiguration(locations = {"classpath:application.yml"})
public class DafAutoConfigurationTest {

    @Test
    public void testConfiguration(){
        System.out.println("testConfiguration");
    }
}
