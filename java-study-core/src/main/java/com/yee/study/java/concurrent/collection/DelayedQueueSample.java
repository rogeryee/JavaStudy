package com.yee.study.java.concurrent.collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * DelayedQueue 使用示例
 *
 * @author Roger.Yi
 */
public class DelayedQueueSample
{
    private static final Logger logger = LoggerFactory.getLogger(DelayedQueueSample.class);

    public static void main(String[] args) throws Exception
    {
        // 缓存中保存着4个项目,每个项目分别在2秒后过期
        String[] cities = new String[]{"Shanghai", "Beijing", "Guangzhou", "HongKong"};
        Cache<String, String> cache = new Cache<>();

        long liveTime = 1000, interval = 2000;
        for (String city : cities)
        {
            liveTime += interval;
            cache.put(city, city, liveTime);
        }

        Thread.sleep(10000);
    }
}

/**
 * 缓存类
 *
 * @param <K>
 * @param <V>
 */
class Cache<K, V>
{
    private static final Logger logger = LoggerFactory.getLogger(DelayedQueueSample.class);

    /**
     * 缓存Map
     */
    public ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();

    /**
     * 延时队列
     */
    public DelayQueue<CachedItem<K>> queue = new DelayQueue<>();

    public void put(K k, V v, long duration)
    {
        V v2 = map.put(k, v);
        CachedItem<K> tmpItem = new CachedItem<>(k, duration);
        if (v2 != null)
        {
            queue.remove(tmpItem);
        }
        queue.put(tmpItem);
    }

    public Cache()
    {
        Thread t = new Thread()
        {
            @Override
            public void run()
            {
                checkAndClearExpiredItem();
            }
        };
        t.setDaemon(true);
        t.start();
    }

    public void checkAndClearExpiredItem()
    {
        while (true)
        {
            CachedItem<K> delayedItem = queue.poll();
            if (delayedItem != null)
            {
                map.remove(delayedItem.getT());
                logger.info("Remove expired item [{}] from cache.", delayedItem.getT());
            }
            try
            {
                Thread.sleep(300);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}

/**
 * 缓存对象
 *
 * @param <T>
 */
class CachedItem<T> implements Delayed
{
    private T t;
    private long duration;
    private long expiredTime;

    /**
     * @param t
     * @param duration
     */
    public CachedItem(T t, long duration)
    {
        this.setT(t);
        this.duration = duration;
        this.expiredTime = TimeUnit.NANOSECONDS.convert(duration, TimeUnit.MILLISECONDS) + System.nanoTime();
    }

    @Override
    public int compareTo(Delayed o)
    {
        if (o == null)
            return 1;

        if (o == this)
            return 0;

        if (o instanceof CachedItem)
        {
            CachedItem<T> tmpDelayedItem = (CachedItem<T>) o;
            if (duration > tmpDelayedItem.duration)
            {
                return 1;
            }
            else if (duration == tmpDelayedItem.duration)
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }
        long diff = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return diff > 0 ? 1 : diff == 0 ? 0 : -1;
    }

    @Override
    public long getDelay(TimeUnit unit)
    {
        return unit.convert(expiredTime - System.nanoTime(), unit);
    }

    public T getT()
    {
        return t;
    }

    public void setT(T t)
    {
        this.t = t;
    }

    @Override
    public int hashCode()
    {
        return t.hashCode();
    }

    @Override
    public boolean equals(Object object)
    {
        if (object instanceof CachedItem)
        {
            return object.hashCode() == hashCode() ? true : false;
        }
        return false;
    }
}
