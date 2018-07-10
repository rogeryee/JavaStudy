package com.yee.study.spring.cloud.demo.provider;

import com.yee.study.spring.cloud.util.YamlUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.io.IOException;

/**
 * 示例中服务提供者（Provider）引用入口
 *
 * 本例中没有使用到 Eureka的注册方式
 *
 * @author Roger.Yi
 */
@SpringBootApplication(exclude = EurekaClientAutoConfiguration.class)
public class ProviderApp {

    @Bean
    public PropertySourcesPlaceholderConfigurer loadYml() throws IOException {
        return YamlUtil.loadYaml("demo/provider.yml");
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProviderApp.class, args);
    }
}
