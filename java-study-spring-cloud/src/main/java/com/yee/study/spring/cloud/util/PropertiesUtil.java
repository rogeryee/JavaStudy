package com.yee.study.spring.cloud.util;

import com.yee.study.spring.cloud.demo.provider.ProviderApp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Roger.Yi
 */
public class PropertiesUtil {

    /**
     * 根据path加载*.properties文件
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Properties loadProperties(String path) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = ProviderApp.class.getClassLoader().getResourceAsStream(path);
        properties.load(inputStream);
        return properties;
    }
}
