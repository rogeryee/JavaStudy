package com.yee.study.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 缺省Redis连接实现类。
 *
 * @author Roger.Yi
 */
public class DefaultRedisConnection implements RedisConnection
{
    private Log logger = LogFactory.getLog(getClass());

    /**
     * Jedis对象
     */
    private Jedis jedis;

    /**
     * Jedis池对象
     */
    private JedisPool pool;

    public DefaultRedisConnection(JedisPool pool)
    {
        this.pool = pool;
        jedis = this.pool.getResource();
    }

    public Jedis getJedis()
    {
        return jedis;
    }

    @SuppressWarnings("deprecation")
    public void close()
    {
        try
        {
            pool.returnResource(jedis);
        }
        catch (Throwable e)
        {
            logger.error("Return jedis resource meet error.", e);
        }
    }

    @SuppressWarnings("deprecation")
    public void closeBroken()
    {
        try
        {
            pool.returnBrokenResource(jedis);
        }
        catch (Throwable e)
        {
            logger.error("Return jedis resource meet error.", e);
        }
    }

    public void forceClose()
    {
        try
        {
            jedis.disconnect();
        }
        catch (Throwable e)
        {
        }
    }

}
