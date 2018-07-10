package com.yee.study.spring.cloud.demo.consumer.controller;

import com.yee.study.spring.cloud.demo.consumer.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Roger.Yi
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * curl 'http://localhost:8082/user/get/1'
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public UserInfo get(@PathVariable Long id) {
        String url = "http://localhost:8081/user/get/" + id;
        return restTemplate.getForObject(url, UserInfo.class);
    }
}
