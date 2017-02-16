package com.yee.study.java.concurrent;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 针对LockWithExclusiveMode的测试类
 *
 * @author: RogerYee
 */
public class LockWithExclusiveModeTest
{
    @Test
    public void test()
    {
        // 产生一个排他锁
        Lock lock = new LockWithExclusiveMode();
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
