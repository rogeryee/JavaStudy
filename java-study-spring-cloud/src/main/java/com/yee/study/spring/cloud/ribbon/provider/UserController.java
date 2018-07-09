package com.yee.study.spring.cloud.ribbon.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Roger.Yi
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

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

        logService();
        
        if(id == 1l)
            return "Roger";
        return "Jason";
    }
    
    private void logService() {
        logger.info("Access Host=[{}] Port=[{}] ServiceId=[{}]", registration.getHost(), registration.getPort(), registration.getServiceId());
    }
}
