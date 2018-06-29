package com.yee.study.spring.cloud.demo.provider.dao;

import com.yee.study.spring.cloud.demo.provider.model.User;

/**
 * @author Roger.Yi
 */
public interface UserDao {

    User findById(long id);
}
