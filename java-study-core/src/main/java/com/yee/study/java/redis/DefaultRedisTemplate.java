package com.yee.study.java.redis;

import com.yee.study.util.CollectionUtil;
import org.springframework.util.Assert;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Tuple;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缺省Redis模版实现类。
 *
 * @author Roger.Yi
 */
public class DefaultRedisTemplate implements RedisTemplate
{
    /**
     * 数据库索引
     */
    private Integer dbIndex;

    /**
     * Redis连接工厂
     */
    private RedisConnectionFactory connectionFactory;

    /**
     * 连接池
     */
    private Map<Long, RedisConnection> connections = new ConcurrentHashMap<Long, RedisConnection>();

    public static interface RedisCallback<T>
    {
        T doInRedis(Jedis jedis) throws Throwable;
    }

    public <T> T execute(RedisCallback<T> action)
    {
        Assert.notNull(action, "Callback object must not be null");
        RedisConnection conn = connectionFactory.getConnection();

        try
        {
            connections.put(Thread.currentThread().getId(), conn);

            Jedis jedis = conn.getJedis();
            if (dbIndex != null)
            {
                jedis.select(dbIndex);
            }

            T retObj = action.doInRedis(jedis);
            conn.close();
            return retObj;
        }
        catch (Throwable ex)
        {
            conn.closeBroken();
            throw new RuntimeException(ex.getMessage(), ex);
        }
        finally
        {
            connections.remove(Thread.currentThread().getId());
        }
    }

    public void closeConnections(Set<Long> consumerThreadIds)
    {
        for (long tid : consumerThreadIds)
        {
            RedisConnection conn = connections.remove(tid);
            if (conn != null)
            {
                try
                {
                    conn.forceClose();
                }
                catch (Throwable e)
                {
                }
            }
        }
    }

    public String setValue(final String key, final String value)
    {
        return execute(new RedisCallback<String>()
        {
            public String doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.set(key, value);
            }
        });
    }

    public String setValue(final String key, final String value, final int expireSeconds)
    {
        return execute(new RedisCallback<String>()
        {
            public String doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.setex(key, expireSeconds, value);
            }
        });
    }

    public boolean setNxValue(final String key, final String value, final int expireSeconds)
    {
        return execute(new RedisCallback<Boolean>()
        {
            public Boolean doInRedis(Jedis jedis) throws Throwable
            {
                Long setResult = jedis.setnx(key, value);
                if (setResult == 1L)
                {
                    jedis.expire(key, expireSeconds);
                    return Boolean.valueOf(true);
                }
                else
                {
                    return Boolean.valueOf(false);
                }
            }
        }).booleanValue();
    }


    public String setValue(String key, String value, Date expireTime)
    {
        return setValue(key, value, calcExpireSeconds(expireTime));
    }

    public Long setnx(final String key, final String value)
    {
        return execute(new RedisCallback<Long>()
        {
            public Long doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.setnx(key, value);
            }
        });
    }

    public String getValue(final String key)
    {
        return execute(new RedisCallback<String>()
        {
            public String doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.get(key);
            }
        });
    }

    public Long removeValue(final String key)
    {
        return execute(new RedisCallback<Long>()
        {
            public Long doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.del(key);
            }
        });
    }

    public Long expire(final String key, final int seconds)
    {
        return execute(new RedisCallback<Long>()
        {
            public Long doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.expire(key, seconds);
            }
        });
    }

    public Long expire(String key, Date expireTime)
    {
        return expire(key, calcExpireSeconds(expireTime));
    }

    public Long ttl(final String key)
    {
        return execute(new RedisCallback<Long>()
        {
            public Long doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.ttl(key);
            }
        });
    }

    public boolean exists(final String key)
    {
        return execute(new RedisCallback<Boolean>()
        {
            public Boolean doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.exists(key);
            }
        });
    }

    public Set<String> keys(final String kayPattern)
    {
        return execute(new RedisCallback<Set<String>>()
        {
            public Set<String> doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.keys(kayPattern);
            }
        });
    }

    public long removeValues(final String keyPattern)
    {
        return execute(new RedisCallback<Long>()
        {
            public Long doInRedis(Jedis jedis) throws Throwable
            {
                Pipeline pl = jedis.pipelined();
                Set<String> keys = jedis.keys(keyPattern);
                for (String key : keys)
                {
                    pl.del(key);
                }
                pl.sync();

                return (long) keys.size();
            }
        });
    }

    public String lpop(final String list)
    {
        return execute(new RedisCallback<String>()
        {
            public String doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.lpop(list);
            }
        });
    }

    public String lpeek(final String list)
    {
        return execute(new RedisCallback<String>()
        {
            public String doInRedis(Jedis jedis) throws Throwable
            {
                List<String> values = jedis.lrange(list, 0, 0);
                if (values.isEmpty())
                {
                    return null;
                }
                else
                {
                    return values.get(0);
                }
            }
        });
    }

    public void rpush(final String list, final String value)
    {
        execute(new RedisCallback<String>()
        {
            public String doInRedis(Jedis jedis) throws Throwable
            {
                jedis.rpush(list, value);
                return null;
            }
        });
    }

    public String blpop(final int timeout, final String... lists)
    {
        return execute(new RedisCallback<String>()
        {
            public String doInRedis(Jedis jedis) throws Throwable
            {
                List<String> values = jedis.blpop(timeout, lists);
                if (values == null)
                {
                    return null;
                }

                if (values.size() < 2)
                {
                    return null;
                }

                return values.get(1);
            }
        });
    }

    public String getSet(final String key, final String value)
    {
        return execute(new RedisCallback<String>()
        {
            public String doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.getSet(key, value);
            }
        });
    }

    public long incrBy(final String key, final long increment)
    {
        return execute(new RedisCallback<Long>()
        {
            public Long doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.incrBy(key, increment);
            }
        });
    }

    public long incrByEx(final String key, final long increment, final int expireSeconds)
    {
        return execute(new RedisCallback<Long>()
        {
            public Long doInRedis(Jedis jedis) throws Throwable
            {
                Pipeline pl = jedis.pipelined();

                Response<Long> newVal = pl.incrBy(key, increment);
                pl.expire(key, expireSeconds);
                pl.sync();

                return newVal.get();
            }
        });
    }

    public long incrByEx(String key, long increment, Date expireTime)
    {
        return incrByEx(key, increment, calcExpireSeconds(expireTime));
    }

    public long incrByWithInitEx(final String key, final long increment, final int initExpireSeconds)
    {
        return execute(new RedisCallback<Long>()
        {
            public Long doInRedis(Jedis jedis) throws Throwable
            {
                long newVal = jedis.incrBy(key, increment);
                if (newVal == increment)
                {
                    jedis.expire(key, initExpireSeconds);
                }

                return newVal;
            }
        });
    }

    public long incrByWithInitEx(String key, long increment, Date initExpireTime)
    {
        return incrByWithInitEx(key, increment, calcExpireSeconds(initExpireTime));
    }

    @Override
    public long zadd(final String key, final double score, final String member)
    {
        return execute(new RedisCallback<Long>()
        {
            public Long doInRedis(Jedis jedis) throws Throwable
            {
                jedis.zadd(key, score, member);
                return jedis.zcard(key);
            }
        }).longValue();
    }

    @Override
    public long zcard(final String key)
    {
        return execute(new RedisCallback<Long>()
        {
            public Long doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.zcard(key);
            }
        }).longValue();
    }

    @Override
    public long zcount(final String key, final Double startScore, final Double endScore)
    {
        return execute(new RedisCallback<Long>()
        {
            public Long doInRedis(Jedis jedis) throws Throwable
            {
                String min = null;
                String max = null;
                if (startScore == null)
                {
                    min = "-inf";
                }
                else
                {
                    min = startScore.toString();
                }
                if (endScore == null)
                {
                    max = "+inf";
                }
                else
                {
                    max = endScore.toString();
                }
                return jedis.zcount(key, min, max);
            }
        }).longValue();
    }

    @Override
    public long zremByScore(final String key, final Double startScore, final Double endScore)
    {
        return execute(new RedisCallback<Long>()
        {
            public Long doInRedis(Jedis jedis) throws Throwable
            {
                String min = null;
                String max = null;
                if (startScore == null)
                {
                    min = "-inf";
                }
                else
                {
                    min = startScore.toString();
                }
                if (endScore == null)
                {
                    max = "+inf";
                }
                else
                {
                    max = endScore.toString();
                }
                return jedis.zremrangeByScore(key, min, max);
            }
        }).longValue();
    }

    @Override
    public Double zrangeGetScore(final String key, final long pos)
    {
        return execute(new RedisCallback<Double>()
        {
            public Double doInRedis(Jedis jedis) throws Throwable
            {
                Set<Tuple> elements = jedis.zrangeWithScores(key, pos, pos);
                if (CollectionUtil.isEmpty(elements))
                {
                    return null;
                }
                return elements.iterator().next().getScore();
            }
        });
    }

    public void flushDb()
    {
        execute(new RedisCallback<String>()
        {
            public String doInRedis(Jedis jedis) throws Throwable
            {
                return jedis.flushDB();
            }
        });
    }

    protected int calcExpireSeconds(Date expireTime)
    {
        return (int) Math.ceil((double) (expireTime.getTime() - System.currentTimeMillis()) / 1000L);
    }

    public Integer getDbIndex()
    {
        return dbIndex;
    }

    public void setDbIndex(Integer dbIndex)
    {
        this.dbIndex = dbIndex;
    }

    public RedisConnectionFactory getConnectionFactory()
    {
        return connectionFactory;
    }

    public void setConnectionFactory(RedisConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
    }
}
