package com.yee.study.java.redis;

/**
 * Redis实现的连接工厂接口定义类。
 *
 * @author Roger.Yi
 */
public interface RedisConnectionFactory
{
    /**
     * 获得Redis连接
     *
     * @return
     */
    RedisConnection getConnection();
}
