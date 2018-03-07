package com.yee.study.java.core.concurrent.collection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;

/**
 * CopyOnWriteArrayList 使用示例
 *
 * @author Roger.Yi
 */
public class CopyOnWriteArrayListSample
{
    public static void main(String[] args) throws InterruptedException
    {
        // CopyOnWriteArrayList可以保证在多线程的情况下set数据的一致性
        List<Double> list = new CopyOnWriteArrayList<>();
        Thread t1 = new TestThread(list, 10000);
        Thread t2 = new TestThread(list, 10000);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertEquals(20000, list.size());

        // HashSet无法保证在多线程的情况下set数据的一致性
        list = new ArrayList<>();
        Thread t3 = new TestThread(list, 10000);
        Thread t4 = new TestThread(list, 10000);
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        assertEquals(20000, list.size());
    }
}
