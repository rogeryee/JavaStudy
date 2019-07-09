package com.yee.study.spring.boot.samples.jpa.dao;

import com.yee.study.spring.boot.samples.jpa.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserDao定义类
 *
 * @author Roger.Yi
 */
@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, Integer> {
    /**
     * 根据名字获取用户
     * @param name
     * @return
     */
    public UserInfo findByName(String name);
}
