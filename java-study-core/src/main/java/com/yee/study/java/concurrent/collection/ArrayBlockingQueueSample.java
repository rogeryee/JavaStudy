package com.yee.study.java.concurrent.collection;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import static org.junit.Assert.assertEquals;

/**
 * ArrayBlockingQueue使用示例
 * <p>
 * ArrayBlockingQueue是“线程安全”的队列，而LinkedList是非线程安全的。
 * <p>
 * 下面是“多个线程同时操作并且遍历queue”的示例
 * (01) 当queue是ArrayBlockingQueue对象时，程序能正常运行。
 * (02) 当queue是LinkedList对象时，程序会产生ConcurrentModificationException异常。
 *
 * @author Roger.Yi
 */
public class ArrayBlockingQueueSample
{
    public static void main(String[] args) throws Exception
    {
        // ArrayBlockingQueue
        Queue<Double> queue = new ArrayBlockingQueue<>(20);
        Thread t1 = new TestThread(queue, 10, true);
        Thread t2 = new TestThread(queue, 10, true);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertEquals(20, queue.size());

        // LinkedList
        queue = new LinkedList<>();
        Thread t3 = new TestThread(queue, 10, true);
        Thread t4 = new TestThread(queue, 10, true);
        t3.start();
        t4.start();
        t3.join();
        t4.join();
        assertEquals(20, queue.size());
    }
}
