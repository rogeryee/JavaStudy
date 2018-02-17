package com.yee.study.java.junit.mockito.sample;

/**
 * @author Roger.Yi
 */
public interface UserDao
{
    /**
     * 保存用户
     * @param user
     */
    String save(User user);

    /**
     * 根据姓名获取用户
     * @param name
     * @return
     */
    User findByName(String name);
}
