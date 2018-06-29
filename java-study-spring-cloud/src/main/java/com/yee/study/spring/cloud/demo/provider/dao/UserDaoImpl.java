package com.yee.study.spring.cloud.demo.provider.dao;

import com.yee.study.spring.cloud.demo.provider.model.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Roger.Yi
 */
@Repository
public class UserDaoImpl implements UserDao, InitializingBean {

    private static Map<Long, User> mockDb;

    @Override
    public User findById(long id) {
        return mockDb.get(id);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        mockDb = new HashMap<>();

        User user = new User();
        user.setId(1L);
        user.setName("Roger");
        user.setAge(31);
        mockDb.put(user.getId(), user);

        user = new User();
        user.setId(2L);
        user.setName("Phoebe");
        user.setAge(32);
        mockDb.put(user.getId(), user);

        user = new User();
        user.setId(3L);
        user.setName("Andy");
        user.setAge(33);
        mockDb.put(user.getId(), user);

        user = new User();
        user.setId(4L);
        user.setName("Jason");
        user.setAge(34);
        mockDb.put(user.getId(), user);

        user = new User();
        user.setId(5L);
        user.setName("Gary");
        user.setAge(35);
        mockDb.put(user.getId(), user);
    }
}
