package com.yee.study.java.core.concurrent.lock.spin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 简单自旋锁实现（非重入锁）
 * <p>
 * 自旋锁是采用让当前线程不停地的在循环体内执行实现的，当循环的条件被其他线程改变时才能进入临界区
 *
 * {@link com.yee.study.java.core.concurrent.lock.spin.SimpleSpinLockTest}
 * @author Roger.Yi
 */
public class SimpleSpinLock implements Lock {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSpinLock.class);

    private AtomicReference<Thread> owner = new AtomicReference<>();

    @Override
    public void lock() {
        // 循环获取锁，直到获取到锁
        Thread current = Thread.currentThread();
        while (!owner.compareAndSet(null, current)) {
            
        }

        LOGGER.info("{} got the lock.", current.getName());
    }

    @Override
    public void unlock() {
        Thread current = Thread.currentThread();

        if(current == owner.get()) {
            LOGGER.info("{} released the lock.", current.getName());
            owner.set(null);
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
