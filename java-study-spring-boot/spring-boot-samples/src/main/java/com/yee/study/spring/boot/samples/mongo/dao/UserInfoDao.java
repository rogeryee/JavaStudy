package com.yee.study.spring.boot.samples.mongo.dao;

import com.yee.study.spring.boot.samples.mongo.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 * @author Roger.Yi
 */
@Repository
public class UserInfoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(UserInfo userInfo) {
        mongoTemplate.save(userInfo);
    }

    public UserInfo findByName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        UserInfo userInfo = mongoTemplate.findOne(query, UserInfo.class);
        return userInfo;
    }
}
