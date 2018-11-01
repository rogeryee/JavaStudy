package com.yee.study.java.core.concurrent.lock.spin;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;

/**
 * 针对SimpleReentrantSpinLock的测试类
 *
 * @author: RogerYee
 */
public class SimpleReentrantSpinLockTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleReentrantSpinLockTest.class);

    /**
     * 测试多个线程之间锁的排他性
     */
    @Test
    public void test() {
        // 产生一个自旋锁
        final Lock lock = new SimpleReentrantSpinLock();

        // 创建3个线程,观察它们的执行情况
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {
                private void doFirst() {
                    try {
                        lock.lock();
                        LOGGER.info("{} got the lock and doing the first job.", Thread.currentThread().getName());
                        Thread.sleep(2000l);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }

                private void doSecond() {
                    try {
                        lock.lock();
                        LOGGER.info("{} got the lock and doing the second job.", Thread.currentThread().getName());
                        Thread.sleep(2000l);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }

                @Override
                public void run() {
                    while (true) {
                        doFirst();
                        doSecond();
                    }
                }
            }).start();
        }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReentrant() {
        final Lock lock = new SimpleReentrantSpinLock();

        lock.lock();
        lock.lock();
        lock.lock();
        lock.unlock();
        lock.unlock();
        lock.unlock();
    }
}
