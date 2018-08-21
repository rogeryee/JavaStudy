package com.yee.study.spring.cloud.hystrix.consumer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author Roger.Yi
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * curl 'http://localhost:8091/user/1'
     *
     * 当服务提供者正常工作时，可以返回正确的用户；
     * 当关闭服务提供者后，会调用 getByIdFallback （fallbackMethod）， 返回默认用户
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    @HystrixCommand(fallbackMethod = "findByIdFallback")
    public String findById(@PathVariable Long id) {
        String url = "http://SERVICE-PROVIDER/user/" + id; // 用服务名访问
        return restTemplate.getForObject(url, String.class);
    }

    public String findByIdFallback(Long id) {
        return "User with fallback";
    }
}
