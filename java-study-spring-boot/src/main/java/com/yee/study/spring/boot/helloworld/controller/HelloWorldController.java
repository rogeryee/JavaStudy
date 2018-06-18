package com.yee.study.spring.boot.helloworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 第一个Restful风格Controller
 *
 * 注意 @RestController = @Controller + @ResponseBody
 * @author Roger.Yi
 */
@RequestMapping("helloworld")
@RestController
public class HelloWorldController {

    /**
     * http://localhost:8080/helloworld/sayhello?name=Roger
     * @param name
     * @return
     */
    @RequestMapping("/sayhello/{name}")
    public String helloWorld(@PathVariable String name) {
        return "Hello, my name is " + name;
    }
}
