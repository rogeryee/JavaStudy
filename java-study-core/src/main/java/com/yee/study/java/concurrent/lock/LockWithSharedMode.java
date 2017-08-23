package com.yee.study.java.concurrent.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于AbstractQueuedSynchronizer的共享锁的实现,
 * 该共享锁根据构造器传入的count表示在同一时刻，只能有数量为count个线程能够并行访问，超过限制的其他线程进入阻塞状态。
 *
 * 测试类 {@link com.yee.study.java.concurrent.lock.LockWithSharedModeTest}
 *
 * @author RogerYee
 */
public class LockWithSharedMode implements Lock
{
    private final Sync sync;

    private static final class Sync extends AbstractQueuedSynchronizer
    {
        private static final long serialVersionUID = 1L;

        Sync(int count)
        {
            if (count <= 0)
            {
                throw new IllegalArgumentException("count must large than zero.");
            }

            setState(count);
        }

        public int tryAcquireShared(int reduceCount)
        {
            for(;;)
            {
                int current = getState();
                int newCount = current - reduceCount;

                if (newCount < 0 || compareAndSetState(current, newCount))
                {
                    return newCount;
                }
            }
        }

        public boolean tryReleaseShared(int returnCount)
        {
            for (; ; )
            {
                int current = getState();
                int newCount = current + returnCount;

                if (compareAndSetState(current, newCount))
                {
                    return true;
                }
            }
        }
    }

    public LockWithSharedMode(int count)
    {
        sync = new Sync(count);
    }

    public void lock()
    {
        sync.acquireShared(1);
    }

    public void lockInterruptibly() throws InterruptedException
    {
        sync.acquireSharedInterruptibly(1);
    }

    public boolean tryLock()
    {
        return sync.tryAcquireShared(1) >= 0;
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
    {
        return sync.tryAcquireSharedNanos(1, unit.toNanos(time));
    }

    public void unlock()
    {
        sync.releaseShared(1);
    }

    public Condition newCondition()
    {
        return null;
    }
}
