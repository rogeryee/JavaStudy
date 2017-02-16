package com.yee.study.java.concurrent;

import java.util.concurrent.locks.Lock;

/**
 * 用于测试锁的工作类
 *
 * @author: RogerYee
 */
public class LockWorker extends Thread
{
    private int seq;
    private long interval;
    private long start;
    private Lock lock;

    public LockWorker(int seq, Lock lock, long start)
    {
        this.seq = seq;
        this.interval = 1000L;
        this.lock = lock;
        this.start = start;
    }

    public void run()
    {
        while (true)
        {
            try
            {
                lock.lock();
                System.out.println("第" + ((System.currentTimeMillis() - start) / 1000) + "秒: Worker-" + seq + " is running.");
                Thread.sleep(interval);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            finally
            {
                lock.unlock();
            }

            try
            {
                // 如果已经释放锁, 需要休眠一下让其他的线程有获取锁的机会
                Thread.sleep(interval);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
