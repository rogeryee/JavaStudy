package com.yee.study.java.concurrent.lock;

import org.junit.Test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 针对ReentrantLock的测试类
 *
 * @author: RogerYee
 */
public class ReentrantLockTest
{
    @Test
    public void test()
    {
        // 产生一个排他锁
        final Lock lock = new ReentrantLock(true);

//        // 创建10个线程,观察它们的执行情况
//        for (int i = 1; i <= 10; i++)
//        {
//            LockWorker w = new LockWorker(i, lock, start);
//            w.start();
//        }
        new Thread(new Runnable()
        {
            private String name = "Thread1";

            public void run()
            {
                try
                {
                    lock.lock();
                    while(true)
                    {

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(new Runnable()
        {
            private String name = "Thread2";

            public void run()
            {
                try
                {
                    Thread.sleep(1000L);
                    lock.lock();
                    while(true)
                    {

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(new Runnable()
        {
            private String name = "Thread3";

            public void run()
            {
                try
                {
                    Thread.sleep(3000L);
                    lock.lock();
                    while(true)
                    {

                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally
                {
                    lock.unlock();
                }
            }
        }).start();

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
