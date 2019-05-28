package com.yee.study.java.test.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Roger.Yi
 */
@Service
@Transactional
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String name)
    {
        try
        {
            return userDao.findByName(name);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Query user meet error.", e);
        }
    }

    @Override
    public String add(User user)
    {
        User oldUser = userDao.findByName(user.getName());
        if (oldUser != null)
        {
            throw new RuntimeException("User already exists.");
        }
        try
        {
            return userDao.save(user);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Save user meet error.", e);
        }
    }

    public void setUserDao(UserDao userDao)
    {
        this.userDao = userDao;
    }
}
