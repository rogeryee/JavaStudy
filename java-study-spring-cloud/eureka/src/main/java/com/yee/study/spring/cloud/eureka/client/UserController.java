package com.yee.study.spring.cloud.eureka.client;

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

    /**
     * curl 'http://localhost:8081/user/get/1'
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public String get(@PathVariable Long id) {
        if (id == 1l)
            return "Roger";

        return "Jason";
    }
}
