package com.yee.study.mongodb.spring;

import com.mongodb.MongoClient;

/**
 * MongoDb客户端工厂接口定义类。
 *
 * @author Roger.Yi
 */
public interface MongoClientFactory {
    /**
     * 获得Mongo客户端。
     *
     * @return
     */
    MongoClient getMongoClient();

    /**
     * 销毁
     */
    void destroy();
}
