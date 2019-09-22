package com.yee.study.spring.cloud.feign.consumer.controller;

import com.yee.study.spring.cloud.feign.provider.ProviderCallback;
import com.yee.study.spring.cloud.feign.provider.controller.RegisterRequest;
import com.yee.study.spring.cloud.feign.provider.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public User findById(@PathVariable Long id) {
        User user = userClient.findById(id);
        logger.info("Get user, rep = " + user);
        return user;
    }

    /**
     * curl -l -H "Content-type: application/json" -X POST -d '{"id":1,"name":"Phoebe","password":"123456","email":"phoebe@apple.com"}' 'http://localhost:8091/user/register'
     *
     * @param request
     */
    @PostMapping(value = "/register")
    public void register(@RequestBody RegisterRequest request) {
        userClient.register(request);
    }

    @GetMapping("/test")
    public void test() {
        userClient.asyncGetUser("aaaa");
        logger.info("test completed. ");
    }

    /**
     * curl 'http://localhost:8091/user/test2'
     */
    @GetMapping("/test2")
    public void test2() {

        ProviderCallback callback = new ProviderCallbackImpl();
        callback.setUrl("http://localhost:8091/user/callback");
        userClient.asyncCallback(callback);
        logger.info("test completed. ");
    }

    @PostMapping(value = "/callback")
    public String callback(@RequestBody User user) {
        logger.info("callback invoked, user = " + user);
        return "callback";
    }

    public static class ProviderCallbackImpl extends ProviderCallback {
        @Override
        public void onResponse(String resp) {
            logger.info("Test2 : callback invoked");
        }
    }

}
