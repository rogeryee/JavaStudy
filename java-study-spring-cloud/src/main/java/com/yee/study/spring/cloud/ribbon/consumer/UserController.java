package com.yee.study.spring.cloud.ribbon.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
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
     * curl 'http://localhost:8083/user/get/1'
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public String get(@PathVariable Long id) {
        String url = "http://SERVICE-PROVIDER/user/get/" + id; // 用服务名访问
        return restTemplate.getForObject(url, String.class);
    }
}
