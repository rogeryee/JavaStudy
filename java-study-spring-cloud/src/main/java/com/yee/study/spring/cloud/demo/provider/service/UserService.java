package com.yee.study.spring.cloud.demo.provider.service;

import com.yee.study.spring.cloud.demo.provider.model.User;

/**
 * @author Roger.Yi
 */
public interface UserService {

    User getUser(long id);
}
