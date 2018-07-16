package com.yee.study.spring.cloud.feign.provider.controller;

import com.yee.study.spring.cloud.feign.provider.model.User;
import jdk.nashorn.internal.runtime.JSONFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务提供者
 * 
 * @author Roger.Yi
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private Map<Long, User> users = new HashMap<>();

    /**
     * 查询用户
     * 
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public User findById(@PathVariable Long id) {
        logger.info("Find user, id = " + id);
        return users.get(id);
    }

    /**
     * 注册用户
     * 
     * @param request
     */
    @PostMapping(value = "/register")
    public void register(@RequestBody RegisterRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request, user);
        users.put(user.getId(), user);
    }
}
