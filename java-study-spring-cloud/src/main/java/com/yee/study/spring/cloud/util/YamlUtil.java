package com.yee.study.spring.cloud.util;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

/**
 * @author Roger.Yi
 */
public class YamlUtil {

    /**
     * 加载Yaml文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static PropertySourcesPlaceholderConfigurer loadYaml(String path) throws IOException {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertySourceLoader loader = new YamlPropertySourceLoader();
        List<PropertySource<?>> propertySources = loader.load("db", new ClassPathResource(path));
        MutablePropertySources sources = new MutablePropertySources();
        for (PropertySource<?> source : propertySources) {
            sources.addLast(source);
        }
        configurer.setPropertySources(sources);
        return configurer;
    }
}
