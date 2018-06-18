package com.yee.study.spring.boot.test;

/**
 * @author Roger.Yi
 */
public interface UserService {
    UserInfo getUserByName(String name);

    int getUserId(String name);
}
