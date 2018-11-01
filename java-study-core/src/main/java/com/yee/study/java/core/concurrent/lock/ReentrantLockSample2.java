package com.yee.study.java.core.concurrent.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Roger.Yi
 */
public class ReentrantLockSample2 {

    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockSample2.class);

//    private Lock lock = new ReentrantLock(); // 默认是非公平锁
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock(); // 默认是非公平锁
    private Lock lock = readWriteLock.writeLock();

    public void doSomething() {
        String threadName = Thread.currentThread().getName();
        logger.info(threadName + " try do something.");
        lock.lock();
        logger.info(threadName + " do something.");
    }

    public void doOtherthing() {
        String threadName = Thread.currentThread().getName();
        logger.info(threadName + " try do otherthing.");
        lock.lock();
        logger.info(threadName + " do otherthing.");
    }
    
    public static void main(String[] args) {

        final ReentrantLockSample2 sample = new ReentrantLockSample2();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    sample.doSomething();
                }
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    sample.doSomething();
                }
            }
        });

        t1.start();
        t2.start();
    }
}
