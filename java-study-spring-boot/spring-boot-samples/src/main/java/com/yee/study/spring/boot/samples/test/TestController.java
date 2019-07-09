package com.yee.study.spring.boot.samples.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 第一个Restful风格Controller
 *
 * 注意 @RestController = @Controller + @ResponseBody
 * @author Roger.Yi
 */
@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    private UserService userService;

    /**
     * http://localhost:8080/test/get/Roger
     * @param name
     * @return
     */
    @RequestMapping("/get/{name}")
    public String getUser(@PathVariable String name) {
        UserInfo userInfo = userService.getUserByName(name);
        return userInfo == null ? "N/A" : userInfo.getName();
    }

    /**
     * http://localhost:8080/test/getId/Roger
     * @param name
     * @return
     */
    @RequestMapping("/getId/{name}")
    public int getUserId(@PathVariable String name) {
        return userService.getUserId(name);
    }
}
