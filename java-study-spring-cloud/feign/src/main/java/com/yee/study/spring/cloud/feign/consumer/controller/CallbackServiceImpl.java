package com.yee.study.spring.cloud.feign.consumer.controller;

import com.yee.study.spring.cloud.feign.Callback;
import com.yee.study.spring.cloud.feign.CallbackService;
import com.yee.study.spring.cloud.feign.provider.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Roger.Yi
 */
@Callback
@RestController()
@RequestMapping("/callback")
public class CallbackServiceImpl extends CallbackService {

    private static final Logger logger = LoggerFactory.getLogger(CallbackServiceImpl.class);

    public String url = "http://localhost:8091/callback/test";

    @Override
    @PostMapping(value = "/test")
    public void onResponse(@RequestBody User user) {
        logger.info("CallbackServiceImpl test onResponse called, user = " + user);
    }
}
