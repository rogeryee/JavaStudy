package com.yee.study.spring.cloud.nepxion.provider.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务提供者
 *
 * @author Roger.Yi
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * 查询用户
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public String findById(@PathVariable Long id) {
        logger.info("Find user, id = " + id);
        return "";
    }
}
