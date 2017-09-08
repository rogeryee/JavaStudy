package com.yee.study.java.designpattern.observer;

/**
 * 发布者接口
 * @author Roger.Yi
 */
public interface Publisher
{
    /**
     * 订阅消息
     * @param sub
     */
    void subscribe(Subscriber sub);

    /**
     * 取消订阅
     * @param sub
     */
    void unsubscribe(Subscriber sub);

    /**
     * 发布消息
     */
    void publish();
}
