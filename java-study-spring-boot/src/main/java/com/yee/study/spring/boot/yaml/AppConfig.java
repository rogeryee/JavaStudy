package com.yee.study.spring.boot.yaml;

import com.yee.study.spring.boot.redis.RedisApp;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

/**
 * @author Roger.Yi
 */
@Configuration
public class AppConfig {
    @Bean
    public PropertySourcesPlaceholderConfigurer properties2() throws Exception {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        List<PropertySource<?>> propertySources = loader.load("db", new ClassPathResource("yaml/custom-yaml.yml"));
        MutablePropertySources sources = new MutablePropertySources();
        for(PropertySource<?> source : propertySources) {
            sources.addLast(source);
        }
        configurer.setPropertySources(sources);
        return configurer;
    }
}
