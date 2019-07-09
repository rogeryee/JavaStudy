package com.yee.study.spring.boot.samples.jpa.controller;

import com.yee.study.spring.boot.samples.jpa.model.UserInfo;
import com.yee.study.spring.boot.samples.jpa.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger.Yi
 */
@RestController
@RequestMapping("/jpa")
public class JpaController {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取用户
     * curl 'http://localhost:8080/jpa/get/Roger'
     *
     * @param name
     * @return
     */
    @RequestMapping("/get/{name}")
    public UserInfo getUser(@PathVariable String name) {
        return userInfoService.getUserInfo(name);
    }
}
