package com.yee.study.spring.boot.redis.controller;

import com.yee.study.spring.boot.mongo.dao.UserInfoDao;
import com.yee.study.spring.boot.mongo.dao.UserInfoRepository;
import com.yee.study.spring.boot.mongo.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger.Yi
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 设置Value
     * curl 'http://localhost:8080/redis/get/person'
     *
     * @param key
     * @return
     */
    @RequestMapping("/get/{key}")
    public String get(@PathVariable String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 设置Value
     * curl 'http://localhost:8080/redis/set/person/Roger'
     *
     * @param key
     * @return
     */
    @RequestMapping("/set/{key}/{value}")
    public String set(@PathVariable String key, @PathVariable String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        return "success";
    }
}
