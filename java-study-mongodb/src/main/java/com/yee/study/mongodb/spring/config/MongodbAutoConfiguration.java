package com.yee.study.mongodb.spring.config;

import com.yee.study.mongodb.spring.DefaultMongoClientFactory;
import com.yee.study.mongodb.spring.DefaultMongoDataSource;
import com.yee.study.mongodb.spring.MongoDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TiRule自动配置类。
 *
 * @author Roger.Yi
 */
@Configuration
public class MongodbAutoConfiguration {

    @Value("${mongodb.client.uri}")
    private String uri;

    @Value("${mongodb.client.db}")
    private String db;

    @Bean
    public MongoDataSource getMongoDataSource() {
        DefaultMongoClientFactory factory = new DefaultMongoClientFactory(uri);
        DefaultMongoDataSource ds = new DefaultMongoDataSource(db);
        ds.setMongoClientFactory(factory);
        return ds;
    }
}
