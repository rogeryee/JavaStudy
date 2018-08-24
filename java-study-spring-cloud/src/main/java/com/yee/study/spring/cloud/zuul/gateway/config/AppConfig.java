package com.yee.study.spring.cloud.zuul.gateway.config;

import com.yee.study.spring.cloud.zuul.gateway.filter.PreRequestFilter;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Roger.Yi
 */
@Configuration
public class AppConfig {

    /**
     * 配置前置请求过滤器
     *
     * @return
     */
    @Bean
    public PreRequestFilter getPreRequestFilter() {
        return new PreRequestFilter();
    }
}
