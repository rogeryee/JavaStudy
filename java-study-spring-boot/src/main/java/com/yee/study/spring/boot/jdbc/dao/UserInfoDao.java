package com.yee.study.spring.boot.jdbc.dao;

import com.yee.study.spring.boot.jdbc.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserInfoDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserInfo findByName(String name) {

        String sql = "select * from UserInfo where name = ?";
        UserInfo user = jdbcTemplate.queryForObject(sql, new RowMapper<UserInfo>() {
            public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                UserInfo user = new UserInfo();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                return user;
            }
        }, name);
        return user;
    }
}
