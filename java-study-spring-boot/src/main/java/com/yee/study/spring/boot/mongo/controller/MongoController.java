package com.yee.study.spring.boot.mongo.controller;

import com.yee.study.spring.boot.mongo.dao.UserInfoDao;
import com.yee.study.spring.boot.mongo.dao.UserInfoRepository;
import com.yee.study.spring.boot.mongo.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger.Yi
 */
@RestController
@RequestMapping("/mongo")
public class MongoController {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoDao userInfoDao;

    /**
     * 获取用户
     * curl 'http://localhost:8080/mongo/get/Roger'
     *
     * @param name
     * @return
     */
    @RequestMapping("/get/{name}")
    public UserInfo getUser(@PathVariable String name) {
        return userInfoRepository.findByName(name);
    }

    /**
     * 获取用户
     * curl 'http://localhost:8080/mongo/add/Roger/Shanghai'
     *
     * @param name
     * @return
     */
    @RequestMapping("/add/{name}/{address}")
    public String add(@PathVariable String name, @PathVariable String address) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setAddress(address);
        userInfoRepository.insert(userInfo);
        return "success";
    }

    /**
     * 获取用户
     * curl 'http://localhost:8080/mongo/get/dao/Phoebe'
     *
     * @param name
     * @return
     */
    @RequestMapping("/get/dao/{name}")
    public UserInfo getUserByDao(@PathVariable String name) {
        return userInfoDao.findByName(name);
    }

    /**
     * 获取用户
     * curl 'http://localhost:8080/mongo/add/dao/Phoebe/Beijing'
     *
     * @param name
     * @return
     */
    @RequestMapping("/add/dao/{name}/{address}")
    public String addByDao(@PathVariable String name, @PathVariable String address) {
        UserInfo userInfo = new UserInfo();
        userInfo.setName(name);
        userInfo.setAddress(address);
        userInfoDao.save(userInfo);
        return "success";
    }
}
