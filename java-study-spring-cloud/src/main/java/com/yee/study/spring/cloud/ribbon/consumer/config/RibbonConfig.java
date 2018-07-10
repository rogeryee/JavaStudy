package com.yee.study.spring.cloud.ribbon.consumer.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ribbon配置类
 *  
 * @author Roger.Yi
 */
@Configuration
public class RibbonConfig {

    @Bean
    public IRule ribbonRule() {
//        return new RandomRule(); // 随机负载
        return new RoundRobinRule(); // 轮训负载
    }
}
