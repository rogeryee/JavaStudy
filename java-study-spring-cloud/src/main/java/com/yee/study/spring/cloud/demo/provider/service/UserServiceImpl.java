package com.yee.study.spring.cloud.demo.provider.service;

import com.yee.study.spring.cloud.demo.provider.dao.UserDao;
import com.yee.study.spring.cloud.demo.provider.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Roger.Yi
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(long id) {
        return userDao.findById(id);
    }
}
