package com.yee.study.java.core.concurrent.lock.spin;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;

/**
 * 针对SimpleSpinLock的测试类
 *
 * @author: RogerYee
 */
public class SimpleSpinLockTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleSpinLockTest.class);

    /**
     * 测试多个线程之间锁的排他性
     */
    @Test
    public void test() {
        // 产生一个自旋锁
        final Lock lock = new SimpleSpinLock();

        // 创建3个线程,观察它们的执行情况，由于是非重入锁，所以每个线程在执行doFirst()和doSecond()方法都需要获取锁
        for (int i = 0; i < 3; i++) {
            new Thread(new Runnable() {

                private void doFirst() {
                    try {
                        lock.lock();
                        LOGGER.info("{} got the lock and doing the first job.", Thread.currentThread().getName());
                        Thread.sleep(1000l);
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
                        Thread.sleep(1000l);
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

    /**
     * 测试 SimpleSpinLock 是否可重入
     */
    @Test
    public void testReentrant() {
        // 产生一个自旋锁
        final Lock lock = new SimpleSpinLock();
        try {
            // 连续2次尝试获取锁，
            // 第1次获取锁成功
            // 第2次尝试获取锁失败
            lock.lock();
            lock.lock();
            lock.unlock();
            lock.unlock();
            LOGGER.info("{} finish job.", Thread.currentThread().getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
