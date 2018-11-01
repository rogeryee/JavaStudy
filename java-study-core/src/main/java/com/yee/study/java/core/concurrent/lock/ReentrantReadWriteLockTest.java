package com.yee.study.java.core.concurrent.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock (可重入读写锁) 使用示例
 *
 * @author Roger.Yee
 */
public class ReentrantReadWriteLockTest
{
    public static void main(String[] args) throws InterruptedException
    {
        // Create 6 threads and 3 of them are to read the data and 3 of them are to write the data
        Thread[] threads = new Thread[6];
        final DataWithLock data = new DataWithLock();
        for (int i = 0; i < 3; i++)
        {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 5; j++)
                {
                    data.write(new Random().nextInt(30));
                }
            });
        }

        for (int i = 3; i < 6; i++)
        {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 5; j++)
                {
                    data.read();
                }
            });
        }

        for (int i = 0; i < 6; i++)
        {
            threads[i].start();
        }

        for (int i = 0; i < 6; i++)
        {
            threads[i].join();
        }
    }
}

/**
 * 数据类
 */
class DataWithLock
{
    private static final Logger logger = LoggerFactory.getLogger(DataWithLock.class);

    /**
     * 数据载体
     */
    private int data;

    /**
     * 读写锁
     */
    private ReadWriteLock rwl = new ReentrantReadWriteLock();

    /**
     * 写数据
     * @param data
     */
    public void write(int data)
    {
        rwl.writeLock().lock();// 获取锁
        String threadName = null;
        try
        {
            try
            {
                threadName = Thread.currentThread().getName();
                logger.info(threadName + " is ready to write data");

                Thread.sleep(20);

                this.data = data;
                logger.info(threadName + " write " + this.data);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        finally
        {
            rwl.writeLock().unlock();// 释放锁
        }

        logger.info(threadName + " finish write " + this.data);
    }

    /**
     * 读数据
     */
    public void read()
    {
        rwl.readLock().lock();// 获取锁
        String threadName = null;
        try
        {
            try
            {
                threadName = Thread.currentThread().getName();
                logger.info(threadName + " is ready to read data");
                Thread.sleep(20);
                logger.info(threadName + " read " + this.data);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        finally
        {
            rwl.readLock().unlock();// 释放锁
        }
        logger.info(threadName + " finish read " + this.data);
    }
}
