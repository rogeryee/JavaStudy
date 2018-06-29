package com.yee.study.spring.cloud.demo.provider.controller;

import com.yee.study.spring.cloud.demo.provider.model.User;
import com.yee.study.spring.cloud.demo.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger.Yi
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    /**
     * curl 'http://localhost:8080/user/get/1'
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public User get(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
