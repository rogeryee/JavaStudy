package com.yee.study.java.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于AbstractQueuedSynchronizer的排他锁的实现
 *
 * @author RogerYee
 */
public class LockWithExclusiveMode implements Lock
{
    private final Sync sync;

    private static class Sync extends AbstractQueuedSynchronizer
    {
        public boolean tryAcquire(int acquires)
        {
            if (compareAndSetState(0, 1))
            {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        protected boolean tryRelease(int releases)
        {
            if (getState() == 0)
                throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }

    public LockWithExclusiveMode()
    {
        sync = new Sync();
    }

    public void lock()
    {
        sync.acquire(1);
    }

    public void lockInterruptibly() throws InterruptedException
    {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock()
    {
        return sync.tryAcquire(1);
    }

    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException
    {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    public void unlock()
    {
        sync.release(1);
    }

    public Condition newCondition()
    {
        return null;
    }
}
