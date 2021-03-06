package com.yee.study.spring.cloud.feign.provider.controller;

import com.yee.study.spring.cloud.feign.CallbackService;
import com.yee.study.spring.cloud.feign.provider.ProviderCallback;
import com.yee.study.spring.cloud.feign.provider.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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

    @Autowired
    private RestTemplate restTemplate;

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

    /**
     * 查询用户
     *
     * @return
     */
    @GetMapping(value = "/async/{callback}")
    public void asyncGetUser(@PathVariable String callback) {
        logger.info("Receive async-get request, callback = " + callback);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String resp = restTemplate.postForObject("http://localhost:8091/user/callback", new User(), String.class);
                logger.info("Callback response = " + resp);
            }
        }).start();

        logger.info("Response async-get request");
    }

    /**
     * 查询用户
     *
     * @return
     */
    @PostMapping(value = "/async/callback")
    public void asyncCallback(@RequestBody ProviderCallback callback) {
        logger.info("Receive async-get request");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String resp = restTemplate.postForObject(callback.getUrl(), new User(), String.class);
                logger.info("Callback response = " + resp);
            }
        }).start();

        logger.info("Response async-get request");
    }

    /**
     * 查询用户
     *
     * @return
     */
    @PostMapping(value = "/async/callback2")
    public void asyncCallback2(@RequestBody CallbackService callback) {
        logger.info("Receive async-get request");

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String resp = restTemplate.postForObject(callback.url, new User(), String.class);
                logger.info("Callback response = " + resp);
            }
        }).start();

        logger.info("Response async-get request");
    }
}
