package com.yee.study.redis;

import com.yee.study.util.DateUtil;
import com.yee.study.util.SleepUtil;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * 缺省RedisTemplate实现类测试类。
 *
 * @author sea.bao
 */
public class DefaultRedisTemplateTest
{

    protected RedisConnectionFactory initRedisConnectionFactory() throws Exception
    {
        // single
        DefaultRedisConnectionFactory cf = new DefaultRedisConnectionFactory();
        cf.setHost("127.0.0.1");
        cf.afterPropertiesSet();
        return cf;
    }

    @Test
    public void test() throws Exception
    {
        final DefaultRedisTemplate template = new DefaultRedisTemplate();
        template.setConnectionFactory(initRedisConnectionFactory());
        template.setDbIndex(1); // select-db
        template.flushDb();

        String key = "ti-lnk:session";
        // 测试原子值设置
        boolean setKeyResult = template.setNxValue(key, "xxx", 30);
        assertTrue(setKeyResult);
        setKeyResult = template.setNxValue(key, "xxx", 30);
        assertFalse(setKeyResult);

        // 多线程原子值设置
        List<Thread> threads = new ArrayList<Thread>();
        final AtomicInteger successCnt = new AtomicInteger();
        final AtomicInteger failedCnt = new AtomicInteger();
        for (int i = 0; i < 20; i++)
        {
            Thread t = new Thread()
            {
                public void run()
                {
                    boolean result = template.setNxValue("for concurrent", "xxx", 30);
                    if (result)
                    {
                        successCnt.incrementAndGet();
                    }
                    else
                    {
                        failedCnt.incrementAndGet();
                    }
                }
            };
            threads.add(t);
            t.start();
        }
        for (Thread t : threads)
        {
            t.join();
        }
        assertEquals(1, successCnt.intValue());
        assertEquals(19, failedCnt.intValue());

        template.setValue(key, "123");
        String value = template.getValue(key);
        assertEquals("123", value);

        assertTrue(-1L == template.ttl(key));
        template.expire(key, 2);
        Thread.sleep(500);
        assertTrue(template.exists(key));
        Thread.sleep(500);
        template.expire(key, DateUtil.rollDate(new Date(), Calendar.SECOND, 2));
        Thread.sleep(500);
        assertTrue(template.exists(key));
        Thread.sleep(2000);
        assertFalse(template.exists(key));

        for (int i = 0; i < 10; i++)
        {
            key = "abc(" + i + ")";
            template.setValue(key, "123");
        }

        for (int i = 0; i < 10; i++)
        {
            key = "cde(" + i + ")";
            template.setValue(key, "123");
        }

        assertEquals(10, template.removeValues("abc(*)"));
        for (int i = 0; i < 10; i++)
        {
            key = "abc(" + i + ")";
            assertEquals(null, template.getValue(key));
        }

        String list = "list-" + System.currentTimeMillis();
        template.rpush(list, "1");
        assertEquals("1", template.lpeek(list));
        assertEquals("1", template.lpop(list));
        assertNull(template.lpop(list));
        template.rpush(list, "1");
        template.rpush(list, "2");
        template.rpush(list, "3");
        assertEquals("1", template.lpop(list));
        assertEquals("2", template.lpop(list));
        assertEquals("3", template.lpop(list));

        String list2 = list + "-2";
        template.rpush(list, "1");
        template.rpush(list2, "2");

        assertEquals("1", template.blpop(0, list, list2));
        assertEquals("2", template.blpop(0, list, list2));
        assertNull(template.blpop(1, list, list2));

        // 测试expire方法
        template.setValue("test-expire-key", "anyvalue", 1);
        assertEquals("anyvalue", template.getValue("test-expire-key"));
        Thread.sleep(1500L);
        assertNull(template.getValue("test-expire-key"));

        template.setValue("test-expire-key2", "anyvalue", DateUtil.rollDate(new Date(), Calendar.SECOND, 1));
        assertEquals("anyvalue", template.getValue("test-expire-key2"));
        Thread.sleep(1500L);
        assertNull(template.getValue("test-expire-key2"));

        // incrBy
        String ibKey = "test-incr-by";
        template.removeValue(ibKey);

        assertEquals(1, template.incrBy(ibKey, 1));
        assertEquals(101, template.incrBy(ibKey, 100));
        assertEquals(-99, template.incrBy(ibKey, -200));
        assertEquals(0, template.incrBy(ibKey, 99));

        String ibxKey = "test-incr-by-ex";
        assertEquals(1, template.incrByWithInitEx(ibxKey, 1, 1));

        SleepUtil.sleep(500L);
        assertEquals(3, template.incrByWithInitEx(ibxKey, 2, 10)); // expire-time-no-change
        assertEquals(6, template.incrByWithInitEx(ibxKey, 3, 100)); // expire-time-no-change

        SleepUtil.sleep(500L);
        assertEquals(1, template.incrByWithInitEx(ibxKey, 1, DateUtil.rollDate(new Date(), Calendar.SECOND, 1))); // expired--reset

        SleepUtil.sleep(500L);
        assertEquals(5, template.incrByWithInitEx(ibxKey, 4, 10)); // expire-time-no-change
        assertEquals(11, template.incrByWithInitEx(ibxKey, 6, 100)); // expire-time-no-change

        SleepUtil.sleep(500L);
        assertEquals(1, template.incrByWithInitEx(ibxKey, 1, 1)); // expired-reset

        template.removeValue(ibKey);

        template.incrByEx(ibKey, 1, 1);
        SleepUtil.sleep(100L);
        assertEquals("1", template.getValue(ibKey));
        SleepUtil.sleep(1000L);
        assertEquals(10, template.incrByEx(ibKey, 10, 1)); // expired
        SleepUtil.sleep(100L);
        assertEquals(20, template.incrByEx(ibKey, 10, DateUtil.rollDate(new Date(), Calendar.SECOND, 1))); // expired
        SleepUtil.sleep(100L);
        assertEquals(30, template.incrByEx(ibKey, 10, 1));
        SleepUtil.sleep(100L);
        assertEquals("30", template.getValue(ibKey));
        SleepUtil.sleep(1000L);
        assertNull(template.getValue(ibKey));

        //测试有序集合
        String sortKey = "test_sorted";
        assertEquals(1L, template.zadd(sortKey, 1.00, "one"));
        assertEquals(2L, template.zadd(sortKey, 2.00, "two"));
        assertEquals(3L, template.zadd(sortKey, 3.00, "three"));
        //测试获取元素
        assertEquals("1.0", template.zrangeGetScore(sortKey, 0L).toString());
        assertNull(template.zrangeGetScore(sortKey, 3));
        //测试更新
        assertEquals(3L, template.zadd(sortKey, 1.25, "one"));
        //测试统计
        assertEquals(3L, template.zcard(sortKey));
        assertEquals(3L, template.zcount(sortKey, null, null));
        assertEquals(2L, template.zcount(sortKey, 1.5, null));
        assertEquals(1L, template.zcount(sortKey, null, 1.5));
        assertEquals(1L, template.zcount(sortKey, 1.5, 2.5));

        //测试删除
        assertEquals(0L, template.zremByScore(sortKey, 4.5, 100.00));
        assertEquals(1L, template.zremByScore(sortKey, 0.5, 1.5));
        assertEquals(2L, template.zcard(sortKey));
        assertEquals(1L, template.zremByScore(sortKey, 2.5, null));
        assertEquals(1L, template.zcard(sortKey));
        assertEquals(1L, template.zremByScore(sortKey, null, null));
        assertEquals(0L, template.zcard(sortKey));
    }
}