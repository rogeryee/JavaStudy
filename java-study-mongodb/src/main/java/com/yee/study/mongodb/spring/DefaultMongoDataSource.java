package com.yee.study.mongodb.spring;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.beans.factory.DisposableBean;

/**
 * 默认的Mongo数据源类。
 *
 * @author Roger.Yi
 */
public class DefaultMongoDataSource implements MongoDataSource, DisposableBean {
    /**
     * Mongo客户端工厂
     */
    private MongoClientFactory mongoClientFactory;

    /**
     * 数据库名称
     */
    private String databaseName;

    public DefaultMongoDataSource(String databaseName) {
        this.databaseName = databaseName;
    }

    @Override
    public MongoDatabase getDatabase() {
        return mongoClientFactory.getMongoClient().getDatabase(databaseName);
    }

    @Override
    public MongoClient getMongoClient() {
        return mongoClientFactory.getMongoClient();
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public void destroy() {
        mongoClientFactory.destroy();
    }

    public MongoClientFactory getMongoClientFactory() {
        return mongoClientFactory;
    }

    public void setMongoClientFactory(MongoClientFactory mongoClientFactory) {
        this.mongoClientFactory = mongoClientFactory;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
