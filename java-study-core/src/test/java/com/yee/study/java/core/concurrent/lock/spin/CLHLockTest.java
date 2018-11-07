package com.yee.study.java.core.concurrent.lock.spin;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;

/**
 * 针对CLHLock的测试类
 *
 * @author: RogerYee
 */
public class CLHLockTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CLHLockTest.class);

    /**
     * 测试多个线程之间锁的排他性
     */
    @Test
    public void test() {
        // 产生一个自旋锁
        final Lock lock = new CLHLock();

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
}
