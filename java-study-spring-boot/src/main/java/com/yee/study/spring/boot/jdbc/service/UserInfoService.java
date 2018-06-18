package com.yee.study.spring.boot.jdbc.service;

import com.yee.study.spring.boot.jdbc.dao.UserInfoDao;
import com.yee.study.spring.boot.jdbc.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户服务接口
 * 
 * @author Roger.Yi
 */
public interface UserInfoService {
    /**
     * 获取用户信息
     * @param name
     * @return
     */
    public UserInfo getUserInfo(String name);
}
