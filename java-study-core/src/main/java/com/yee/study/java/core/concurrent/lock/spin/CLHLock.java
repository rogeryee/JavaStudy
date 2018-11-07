package com.yee.study.java.core.concurrent.lock.spin;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * CLH Lock 实现（自旋锁的一种）
 *
 * @author Roger.Yi
 */
public class CLHLock extends AbstractLock {
    
    private volatile CLHNode tail;

    private static final ThreadLocal<CLHNode> LOCAL = new ThreadLocal<CLHNode>();

    private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(CLHLock.class, CLHNode.class, "tail");

    public void lock() {

        // 为当前尝试获取锁的线程创建CLH节点
        CLHNode node = new CLHNode();
        LOCAL.set(node);

        // 加入等待队列，并获取当前节点的前置节点
        CLHNode preNode = UPDATER.getAndSet(this, node);
        if (preNode != null) {

            while (preNode.isLocked) {

            }

            preNode = null;
            LOCAL.set(node);
        }
    }

    public void unlock() {
        CLHNode node = LOCAL.get();

        if (!UPDATER.compareAndSet(this, node, null)) {
            node.isLocked = false;

        }
        node = null;
    }

    /**
     * CLH 节点
     */
    public static class CLHNode {
        private volatile boolean isLocked = true;
    }
}
