package com.yee.study.spring.boot.helloworld.controller;

import org.hibernate.validator.constraints.EAN;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 第一个Restful风格Controller
 *
 * 注意 @RestController = @Controller + @ResponseBody
 * @author Roger.Yi
 */
@RequestMapping("/helloworld")
@RestController
public class HelloWorldController {

    /**
     * http://localhost:8080/helloworld/sayhello/Roger
     * @param name
     * @return
     */
    @RequestMapping("/sayhello/{name}")
    public String helloWorld(@PathVariable String name) {
        return "Hello, my name is " + name;
    }

    /**
     * http://localhost:8080/helloworld/list
     * @param name
     * @return
     */
    @RequestMapping("/list")
    public List<String> helloWorld() {
        List<String> list = new ArrayList<String>();
        list.add("Roger");
        list.add("Phoebe");
        return list;
    }
}
