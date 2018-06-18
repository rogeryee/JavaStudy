package com.yee.study.spring.boot.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

/**
 * @author Roger.Yi
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApp.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void testGetUserByName() {
        UserInfo userInfo = userService.getUserByName("Roger");
        assertEquals("Roger", userInfo.getName());
    }
}
