package com.yee.study.spring.cloud.eureka.client;

import com.yee.study.spring.cloud.demo.consumer.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Roger.Yi
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private Registration registration;

    @Autowired
    private DiscoveryClient discoveryClient;

    /**
     * curl 'http://localhost:8081/user/get/1'
     * @param id
     * @return
     */
    @GetMapping(value = "/get/{id}")
    public String get(@PathVariable Long id) {
        return "success";
    }

    /**
     * 获取服务注册信息
     *
     * curl 'http://localhost:8081/user/user-instance'
     *
     * @return
     */
    @GetMapping("/user-instance")
    public List<ServiceInstance> showInfo() {
        String serviceId = registration.getServiceId(); // EUREKA-CLIENT-1
        return discoveryClient.getInstances(serviceId);
    }
}
