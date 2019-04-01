package com.yee.study.mongodb.spring;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认的Mongo客户端工厂类。
 *
 * @author Roger.Yi
 */
public class DefaultMongoClientFactory implements MongoClientFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultMongoClientFactory.class);

    /**
     * Mongo客户端
     */
    private MongoClient mongoClient;

    public DefaultMongoClientFactory(String uri) {
        init(uri);
    }

    private void init(String uri) {
        logger.info("factory being init.");
        MongoClientURI connetionStr = new MongoClientURI(uri);
        mongoClient = new MongoClient(connetionStr);
    }

    @Override
    public void destroy() {
        logger.info("destroy client");
        mongoClient.close();
    }

    @Override
    public MongoClient getMongoClient() {
        return mongoClient;
    }
}
