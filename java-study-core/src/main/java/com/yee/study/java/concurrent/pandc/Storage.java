package com.yee.study.java.concurrent.pandc;

/**
 * 产品存储接口定义
 *
 * @author Roger.Yi
 */
public interface Storage
{
    /**
     * 生产产品
     * @param producer
     * @param product
     */
    void produce(String producer, Product product) throws InterruptedException;

    /**
     * 消费产品
     * @param consumer
     */
    void consume(String consumer) throws InterruptedException;
}
