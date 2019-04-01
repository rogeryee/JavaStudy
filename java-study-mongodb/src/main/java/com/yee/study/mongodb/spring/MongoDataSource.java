package com.yee.study.mongodb.spring;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

/**
 * Mongodb数据源类。
 *
 * @author Roger.Yi
 */
public interface MongoDataSource {
    /**
     * 获得MongoDb数据库。
     *
     * @return
     */
    MongoDatabase getDatabase();

    /**
     * 获得Mongo客户端。
     *
     * @return
     */
    MongoClient getMongoClient();

    /**
     * 获得数据库名称。
     *
     * @return
     */
    String getDatabaseName();
}
