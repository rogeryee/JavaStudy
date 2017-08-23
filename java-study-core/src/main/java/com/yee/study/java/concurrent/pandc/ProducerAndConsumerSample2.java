package com.yee.study.java.concurrent.pandc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者/消费者模式 示例1
 * <p>
 * 本例使用了 await()和signal()来更加细粒度的控制线程.
 *
 * @author Roger.Yi
 */
public class ProducerAndConsumerSample2
{
    public static void main(String[] args)
    {
        // 创建产品存储类
        Storage storage = new StorageWithLock(10);

        // 创建3个生产者
        Thread p1 = new Thread(new Producer("Producer1", storage));
        Thread p2 = new Thread(new Producer("Producer2", storage));
        Thread p3 = new Thread(new Producer("Producer3", storage));

        // 创建2个消费者
        Thread c1 = new Thread(new Consumer("Consumer1", storage));
        Thread c2 = new Thread(new Consumer("Consumer2", storage));

        // Starts the thread
        c1.start();
        c2.start();
        p1.start();
        p2.start();
        p3.start();
    }
}

/**
 * 产品存储类
 * 使用 await()和signal()实现了存储功能
 */
class StorageWithLock implements Storage
{
    private static final Logger logger = LoggerFactory.getLogger(StorageWithLock.class);

    // 容量
    private int capacity = 1;

    // 存放产品集合
    private List<Product> products;

    // 锁
    private final Lock lock = new ReentrantLock();

    // 仓库满的条件变量
    private final Condition full = lock.newCondition();

    // 仓库空的条件变量
    private final Condition empty = lock.newCondition();

    public StorageWithLock(int capacity)
    {
        this.capacity = capacity;
        this.products = new LinkedList<>();
    }

    @Override
    public void consume(String consumer) throws InterruptedException
    {
        lock.lock();

        while (this.products.size() == 0)
        {
            logger.info("{} consume failed, and current storage is [{}]", consumer, this.products.size());
            empty.await();
        }

        this.products.remove(this.products.size() - 1);
        logger.info("{} consume the product, and current storage is [{}]", consumer, this.products.size());

        empty.signalAll();
        full.signalAll();

        lock.unlock();
    }

    @Override
    public void produce(String producer, Product product) throws InterruptedException
    {
        lock.lock();

        while (this.products.size() == capacity)
        {
            logger.info("{} produce failed due to reach the capacity, and current storage is [{}]", producer, this.products.size());
            full.await();
        }

        this.products.add(product);
        logger.info("{} produce a product, and current storage is [{}]", producer, this.products.size());

        full.signalAll();
        empty.signalAll();

        lock.unlock();
    }
}

