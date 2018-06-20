package com.yee.study.spring.boot.mongo.dao;

import com.yee.study.spring.boot.mongo.model.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Roger.Yi
 */
@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

    public UserInfo findByName(String name);
}
