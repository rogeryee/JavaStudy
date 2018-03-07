package com.yee.study.java.core.concurrent.collection;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * CopyOnWriteArraySet 使用示例
 *
 * @author Roger.Yi
 */
public class CopyOnWriteArraySetSample
{
    public static void main(String[] args) throws InterruptedException
    {
        // CopyOnWriteArraySet可以保证在多线程的情况下set数据的一致性
        Set<Double> set = new CopyOnWriteArraySet<>();
        Thread t1 = new TestThread(set, 10000);
        Thread t2 = new TestThread(set, 10000);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertEquals(20000, set.size());

        // HashSet无法保证在多线程的情况下set数据的一致性
        set = new HashSet<>();
        Thread t3 = new TestThread(set, 10000);
        Thread t4 = new TestThread(set, 10000);
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        assertEquals(20000, set.size());
    }
}
