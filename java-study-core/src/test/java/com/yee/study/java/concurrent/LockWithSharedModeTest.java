package com.yee.study.java.concurrent;

import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * 针对LockWithSharedMode的测试类
 *
 * @author: RogerYee
 */
public class LockWithSharedModeTest
{
    @Test
    public void test()
    {
        // 产生一个同时支持3个线程访问的共享锁
        Lock lock = new LockWithSharedMode(3);
        long start = System.currentTimeMillis();

        // 创建10个线程,观察它们的执行情况
        for (int i = 1; i <= 10; i++)
        {
            LockWorker w = new LockWorker(i, lock, start);
            w.start();
        }

        try
        {
            while(true)
            {
                Thread.sleep(20000L);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
