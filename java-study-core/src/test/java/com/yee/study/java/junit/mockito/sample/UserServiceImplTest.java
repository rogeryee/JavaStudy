package com.yee.study.java.junit.mockito.sample;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Roger.Yi
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest
{
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDao userDao;

    @Before
    public void setUp()
    {
        // 如果在class前加上 @RunWith(MockitoJUnitRunner.class)，就不需要手动的初始化了
//        MockitoAnnotations.initMocks(this);

        // 如果不用 @Mock 和 @InjectMocks， 则需要如下的方式来初始化
//        userDao = mock(UserDao.class);
//        userService = new UserServiceImpl();
//        userService.setUserDao(userDao);
    }

    @Test
    public void testGetUserByName()
    {
        // 设置方法调用的预期返回
        User rtnUser = new User();
        rtnUser.setId("001");
        rtnUser.setName("admin");
        when(userDao.findByName(anyString())).thenReturn(rtnUser);

        // 验证方法调用
        User user = userService.getUser("admin");
        verify(userDao, times(1)).findByName(anyString());

        // assert 返回值是否和预期一样
        assertNotNull(user);
        assertEquals("admin", user.getName());
    }

    @Test
    public void testAddUser()
    {
        // 设置方法调用的预期返回
        User rtnUser = new User();
        rtnUser.setId("001");
        rtnUser.setName("admin");
        when(userDao.findByName("admin")).thenReturn(rtnUser);
        when(userDao.save(any())).thenReturn("002");

        // 验证方法调用
        User userToAdd = new User();
        userToAdd.setName("Roger");
        String id = userService.add(userToAdd);
        verify(userDao, times(1)).findByName(anyString());
        verify(userDao, times(1)).save(any());

        // assert 返回值是否和预期一样
        assertNotNull(id);
        assertEquals("002", id);
    }
}
