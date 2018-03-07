package com.yee.study.java.core.concurrent.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock (可重入锁) 使用示例
 *
 * {@link #testSynchronized} 使用"synchronized"关键字来控制线程并发打印
 * {@link #testReentrantLock} 使用锁来控制线程并发打印
 *
 * @author Roger.Yee
 */
public class ReentrantLockSample
{
    public static void main(String[] args) throws InterruptedException
    {
        ReentrantLockSample test = new ReentrantLockSample();
        test.testReentrantLock();
//        test.testSynchronized();
    }

    private void testSynchronized() throws InterruptedException
    {
        final OutputterWithReentrantLock outputter = new OutputterWithReentrantLock();

        // Create 2 threads
        Thread t1 = new Thread("Thread1")
        {
            public void run()
            {
                outputter.outputWithSynchronized("Shanghai");
            }
        };

        Thread t2 = new Thread("Thread2")
        {
            public void run()
            {
                outputter.outputWithSynchronized("Beijing");
            }
        };

        t1.start();
        t2.start();
    }

    private void testReentrantLock() throws InterruptedException
    {
        final OutputterWithReentrantLock outputter = new OutputterWithReentrantLock();

        // Create 2 threads
        Thread t1 = new Thread("Thread1")
        {
            public void run()
            {
                outputter.outputWithLock("Shanghai");
            }
        };

        Thread t2 = new Thread("Thread2")
        {
            public void run()
            {
                outputter.outputWithLock("Beijing");
            }
        };

        t1.start();
        t2.start();
    }
}

/**
 * 字符串打印类
 */
class OutputterWithReentrantLock
{
    private static final Logger logger = LoggerFactory.getLogger(OutputterWithReentrantLock.class);

    private Lock lock = new ReentrantLock(); // 默认是非公平锁

    /**
     * 使用锁来控制线程并发打印
     *
     * @param name
     */
    public void outputWithLock(String name)
    {
        lock.lock();// 获取锁
        try
        {
            String threadName = Thread.currentThread().getName();
            logger.info(threadName + " gets the lock and running!");
            for (int i = 0; i < name.length(); i++)
            {
                logger.info(threadName + ": " + name.charAt(i));
            }
        }
        finally
        {
            lock.unlock();// 释放锁
        }
    }

    /**
     * 使用"synchronized"关键字来控制线程并发打印
     *
     * @param name
     */
    public synchronized void outputWithSynchronized(String name)
    {
        String threadName = Thread.currentThread().getName();
        logger.info(threadName + " is running!");
        for (int i = 0; i < name.length(); i++)
        {
            logger.info(threadName + ": " + name.charAt(i));
        }
        logger.info(threadName + " completes!");
    }
}
