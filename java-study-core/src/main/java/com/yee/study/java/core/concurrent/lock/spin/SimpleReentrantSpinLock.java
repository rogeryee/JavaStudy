package com.yee.study.java.core.concurrent.lock.spin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 简单自旋锁实现（可重入锁）
 * <p>
 * 自旋锁是采用让当前线程不停地的在循环体内执行实现的，当循环的条件被其他线程改变时才能进入临界区
 * <p>
 * {@link com.yee.study.java.core.concurrent.lock.spin.SimpleReentrantSpinLockTest}
 *
 * @author Roger.Yi
 */
public class SimpleReentrantSpinLock extends AbstractLock {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReentrantSpinLock.class);

    private AtomicReference<Thread> owner = new AtomicReference<>();

    private int count = 0;

    @Override
    public void lock() {
        Thread current = Thread.currentThread();

        // 对当前线程可重入
        if (current == owner.get()) {
            LOGGER.info("{} already has the lock.", current.getName());
            ++count;
            return;
        }

        while (!owner.compareAndSet(null, current)) {
        }

        LOGGER.info("{} got the lock.", current.getName());
    }

    @Override
    public void unlock() {
        Thread current = Thread.currentThread();
        if (current == owner.get()) {
            if (count > 0) {
                --count;
            } else {
                LOGGER.info("{} released the lock.", current.getName());
                owner.set(null);
            }
        }
    }
}
