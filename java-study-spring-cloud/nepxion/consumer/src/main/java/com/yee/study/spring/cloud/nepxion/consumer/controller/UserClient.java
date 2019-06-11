package com.yee.study.spring.cloud.nepxion.consumer.controller;

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
    String findById(@PathVariable("id") Long id);
}
