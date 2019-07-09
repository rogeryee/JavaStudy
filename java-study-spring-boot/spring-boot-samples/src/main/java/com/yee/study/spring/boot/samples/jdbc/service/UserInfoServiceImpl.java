package com.yee.study.spring.boot.samples.jdbc.service;

import com.yee.study.spring.boot.samples.jdbc.dao.UserInfoDao;
import com.yee.study.spring.boot.samples.jdbc.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 * @author Roger.Yi
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo getUserInfo(String name) {
        return userInfoDao.findByName(name);
    }
}
