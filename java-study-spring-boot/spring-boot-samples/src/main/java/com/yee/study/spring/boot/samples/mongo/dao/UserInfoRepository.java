package com.yee.study.spring.boot.samples.mongo.dao;

import com.yee.study.spring.boot.samples.mongo.model.UserInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Roger.Yi
 */
@Repository
public interface UserInfoRepository extends MongoRepository<UserInfo, String> {

    public UserInfo findByName(String name);
}
