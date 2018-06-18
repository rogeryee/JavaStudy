package com.yee.study.spring.boot.jpa.dao;

import com.yee.study.spring.boot.jpa.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

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
