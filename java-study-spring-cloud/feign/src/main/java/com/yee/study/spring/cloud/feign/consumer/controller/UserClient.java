package com.yee.study.spring.cloud.feign.consumer.controller;

import com.yee.study.spring.cloud.feign.provider.controller.RegisterRequest;
import com.yee.study.spring.cloud.feign.provider.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 用户服务 客户端接口
 *
 * @author Roger.Yi
 */
@FeignClient("service-provider")
public interface UserClient {

    @GetMapping(value = "/user/{id}")
    public User findById(@PathVariable("id") Long id);

    @PostMapping(value = "/user/register")
    public void register(@RequestBody RegisterRequest request);
}
