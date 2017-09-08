package com.yee.study.java.redis;

import java.util.Date;
import java.util.Set;

/**
 * Redis模版接口定义类
 *
 * @author Roger.Yi
 */
public interface RedisTemplate
{
    /**
     * 设置Value
     *
     * @param key
     * @param value
     * @return
     */
    String setValue(String key, String value);

    /**
     * 设置缓存值，同时直接设置过期时间
     *
     * @param key
     * @param value
     * @param expireSeconds 过期秒数
     * @return
     */
    String setValue(String key, String value, int expireSeconds);

    /**
     * 设置缓存值，同时直接设置过期时间
     *
     * @param key
     * @param value
     * @param expireTime 过期时间
     * @return
     */
    String setValue(String key, String value, Date expireTime);

    /**
     * 只在key不存在的情况在设置值
     *
     * @param key
     * @param value
     * @param expireSeconds
     * @return
     */
    boolean setNxValue(final String key, final String value, final int expireSeconds);

    /**
     * 设置value如果该key不存在，然后返回1；否则直接返回0；
     *
     * @param key
     * @param value
     * @return
     */
    Long setnx(String key, String value);

    /**
     * 获得Value
     *
     * @return
     */
    String getValue(String key);

    /**
     * 删除Value
     *
     * @param key
     */
    Long removeValue(String key);

    /**
     * 设置KeyValue的过期时间（秒）
     *
     * @param key
     * @param seconds
     * @return 1成功, 0失败
     */
    Long expire(String key, int seconds);

    /**
     * 设置KeyValue的过期时间（指定时间）
     *
     * @param key
     * @param date
     * @return 1成功, 0失败
     */
    Long expire(String key, Date date);

    /**
     * 获得KeyValue的生存周期（秒）
     *
     * @param key
     * @return
     */
    Long ttl(String key);

    /**
     * 是否存在KeyValue
     *
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 获得Key模式对应的Key列表
     *
     * @param kayPattern
     * @return
     */
    Set<String> keys(String kayPattern);

    /**
     * 删除Key模式对应的数据
     *
     * @param keyPattern
     * @return
     */
    long removeValues(String keyPattern);

    /**
     * 左弹出list值
     *
     * @param list
     * @return
     */
    String lpop(String list);

    /**
     * 左预读list值
     *
     * @param list
     * @return
     */
    String lpeek(String list);

    /**
     * 右压入list值
     *
     * @param list
     * @param value
     */
    void rpush(String list, String value);

    /**
     * 右弹出list值
     *
     * @param timeout
     * @param lists
     * @return
     */
    String blpop(int timeout, String... lists);

    /**
     * 获得并且设置key对应的值
     *
     * @param key
     * @param value
     * @return
     */
    String getSet(String key, String value);

    /**
     * 为key的当前值增加increment(可为负数)
     *
     * @param key
     * @param increment
     * @return 增加后的值
     */
    long incrBy(String key, long increment);

    /**
     * 为key的当前值增加increment(可为负数)，包含超时时间(单位秒)
     *
     * @param key
     * @param increment
     * @param expireSeconds
     * @return 增加后的值
     */
    long incrByEx(String key, long increment, int expireSeconds);

    /**
     * 为key的当前值增加increment(可为负数)，包含超时时间
     *
     * @param key
     * @param increment
     * @param expireTime
     * @return 增加后的值
     */
    long incrByEx(String key, long increment, Date expireTime);

    /**
     * 为key的当前值增加increment(可为负数)，包含初始超时时间(当前值为增加值时设置超时时间)
     *
     * @param key
     * @param increment
     * @param initExpireSeconds
     * @return 增加后的值
     */
    long incrByWithInitEx(String key, long increment, int initExpireSeconds);

    /**
     * 为key的当前值增加increment(可为负数)，包含初始超时时间(当前值为增加值时设置超时时间)
     *
     * @param key
     * @param increment
     * @param initExpireTime
     * @return 增加后的值
     */
    long incrByWithInitEx(String key, long increment, Date initExpireTime);

    /**
     * 把元素加入到有序集合中，按score进行排序,score相同，使用memberVal字母排序
     *
     * @param key       有序集合的key
     * @param score     元素对应分数
     * @param memberVal 元素值
     * @return 当前有序集合的个数
     */
    long zadd(String key, double score, String member);

    /**
     * 返回key对应有序集合的元素个数，如key不存在会报错
     *
     * @param key
     * @return
     */
    long zcard(String key);

    /**
     * 统计有序集合的元素个数
     *
     * @param key        有序集合的key
     * @param startScore
     * @param endScore
     * @return
     */
    long zcount(String key, Double startScore, Double endScore);

    /**
     * 移除范围内的有序集合元素
     *
     * @param key        有序集合的key
     * @param startScore 开始分数，不指定送null
     * @param endScore   结束分数，不指定送null,start和end都送null相当于清空删除有序集合
     * @return 删除的元素个数
     */
    long zremByScore(String key, Double startScore, Double endScore);

    /**
     * 获取指定位置元素的score
     *
     * @param key 有序集合的key
     * @param pos 排序后的位置
     * @return
     */
    Double zrangeGetScore(String key, long pos);

    /**
     * 关闭连接
     *
     * @param consumerThreadIds
     */
    void closeConnections(Set<Long> consumerThreadIds);

    /**
     * 清空整个数据库
     */
    void flushDb();
}
