package com.yee.study.spring.cloud.nepxion.consumer.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserClient userClient;

    /**
     * curl 'http://localhost:8091/user/1'
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public String findById(@PathVariable Long id) {
        logger.info("Consumer user, id = " + id);
        return userClient.findById(id);
    }
}
