package com.yee.study.java.core.concurrent.pandc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * 生产者/消费者模式 示例1
 * <p>
 * 本例使用了 wait()和notify()方法实现了多个线程之间的同步
 * wait()方法：当缓冲区已满/空时，生产者/消费者线程停止自己的执行，放弃锁，使自己处于等等状态，让其他线程执行。
 * notify()方法：当生产者/消费者向缓冲区放入/取出一个产品时，向其他等待的线程发出可执行的通知，同时放弃锁，使自己处于等待状态。
 *
 * @author Roger.Yi
 */
public class ProducerAndConsumerSample1
{
    public static void main(String[] args)
    {
        // 创建产品存储类
        Storage storage = new StorageWithWaitAndNotify(10);

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
 * 使用 wait()和notify()实现了存储功能
 */
class StorageWithWaitAndNotify implements Storage
{
    private static final Logger logger = LoggerFactory.getLogger(StorageWithWaitAndNotify.class);

    // 容量
    private int capacity = 1;

    // 存放产品集合
    private List<Product> products;

    public StorageWithWaitAndNotify(int capacity)
    {
        this.capacity = capacity;
        this.products = new LinkedList<>();
    }

    @Override
    public void produce(String producer, Product product) throws InterruptedException
    {
        synchronized (this.products)
        {
            while (this.products.size() == capacity)
            {
                logger.info("{} produce failed due to reach the capacity, and current storage is [{}]", producer, this.products.size());
                this.products.wait();
            }

            this.products.add(product);
            logger.info("{} produce a product, and current storage is [{}]", producer, this.products.size());
            this.products.notifyAll();
        }
    }

    @Override
    public void consume(String consumer) throws InterruptedException
    {
        synchronized (this.products)
        {
            while (this.products.size() == 0)
            {
                logger.info("{} consume failed, and current storage is [{}]", consumer, this.products.size());
                this.products.wait();
            }

            this.products.remove(this.products.size() - 1);
            logger.info("{} consume the product, and current storage is [{}]", consumer, this.products.size());
            this.products.notifyAll();
        }
    }
}
