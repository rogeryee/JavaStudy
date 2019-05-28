package com.yee.study.java.test.mock;

/**
 * @author Roger.Yi
 */
public interface UserService
{
    /**
     * 获取用户
     * @param name
     * @return
     */
    User getUser(String name);

    /**
     * 新增用户
     * @param user
     * @return
     */
    String add(User user);
}
