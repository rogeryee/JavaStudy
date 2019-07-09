package com.yee.study.spring.boot.samples.jpa.service;

import com.yee.study.spring.boot.samples.jpa.model.UserInfo;

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
