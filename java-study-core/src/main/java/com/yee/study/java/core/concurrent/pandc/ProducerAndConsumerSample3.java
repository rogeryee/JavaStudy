package com.yee.study.java.core.concurrent.pandc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 生产者/消费者模式 示例1
 * <p>
 * 本例使用了阻塞队列(BlockingQueue)方法实现了多个线程之间的同步
 *
 * @author Roger.Yi
 */
public class ProducerAndConsumerSample3
{
    public static void main(String[] args)
    {
        // 创建产品存储类
        Storage storage = new StorageWithBlockingQueue(10);

        // 创建3个生产者
        Thread p1 = new Thread(new Producer("Producer1", storage));
        Thread p2 = new Thread(new Producer("Producer2", storage));
        Thread p3 = new Thread(new Producer("Producer3", storage));

        // 创建2个消费者
        Thread c1 = new Thread(new Consumer("Consumer1", storage));
        Thread c2 = new Thread(new Consumer("Consumer2", storage));

        // Starts the thread
        c1.start();
//        c2.start();
        p1.start();
//        p2.start();
//        p3.start();
    }
}

/**
 * 产品存储类
 * 使用BlockingQueue实现了存储功能
 */
class StorageWithBlockingQueue implements Storage
{
    private static final Logger logger = LoggerFactory.getLogger(StorageWithBlockingQueue.class);

    // 容量
    private int capacity = 1;

    // 存放产品集合
    private BlockingQueue<Product> products;

    public StorageWithBlockingQueue(int capacity)
    {
        this.capacity = capacity;
        this.products = new LinkedBlockingQueue<>(capacity);
    }

    @Override
    public void produce(String producer, Product product) throws InterruptedException
    {
        this.products.put(product);
        logger.info("{} produce a product, and current storage is [{}]", producer, this.products.size());
    }

    @Override
    public void consume(String consumer) throws InterruptedException
    {
        this.products.take();
        logger.info("{} consume the product, and current storage is [{}]", consumer, this.products.size());
    }
}

