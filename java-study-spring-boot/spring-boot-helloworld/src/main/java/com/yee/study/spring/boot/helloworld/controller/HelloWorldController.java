package com.yee.study.spring.boot.helloworld.controller;

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
@RequestMapping("/helloworld")
@RestController
public class HelloWorldController {

    /**
     * http://localhost:8081/helloworld/sayhello/Roger
     * @param name
     * @return
     */
    @RequestMapping("/sayhello/{name}")
    public String helloWorld(@PathVariable String name) {
        return "Hello, my name is " + name;
    }

//    /**
//     * http://localhost:8081/helloworld/sayhello/Roger
//     * @param name
//     * @return
//     */
//    @Async
//    @RequestMapping("/sayhello/asyn/{name}")
//    public String asynHelloWorld(@PathVariable String name) {
//
//        new Thread(() -> {
//            try {
//                System.out.println("enter thread");
//                Thread.sleep(5000);
//                System.out.println("exit thread");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }).start();
//        System.out.println("call asynHelloWorld");
//        return "[Asyn] Hello, my name is " + name;
//    }
//
//    /**
//     * http://localhost:8081/helloworld/list
//     * @param name
//     * @return
//     */
//    @RequestMapping("/list")
//    public List<String> helloWorld() {
//        List<String> list = new ArrayList<String>();
//        list.add("Roger");
//        list.add("Phoebe");
//        return list;
//    }
}
