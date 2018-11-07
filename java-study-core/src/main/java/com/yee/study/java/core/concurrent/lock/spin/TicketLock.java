package com.yee.study.java.core.concurrent.lock.spin;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ticket Lock（自旋锁的一种实现）实现
 * <p>
 * Ticket Lock 虽然解决了公平性的问题（保证FIFO），
 * 但是多处理器系统上，每个进程/线程占用的处理器都在读写同一个变量serviceNum ，
 * 每次读写操作都必须在多个处理器缓存之间进行缓存同步，这会导致繁重的系统总线和内存的流量，大大降低系统整体的性能。
 *
 * @author Roger.Yi
 */
public class TicketLock extends AbstractLock {

    private AtomicInteger serviceNum = new AtomicInteger(0);

    private AtomicInteger ticketNum = new AtomicInteger(0);

    private static final ThreadLocal<Integer> myNum = new ThreadLocal<Integer>();

    public void lock() {
        myNum.set(ticketNum.getAndIncrement());
        while (serviceNum.get() != myNum.get()) {
        }
        ;
    }

    public void unlock() {
        serviceNum.compareAndSet(myNum.get(), myNum.get() + 1);
    }
}
